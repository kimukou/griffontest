package twittersphere2

import gov.nasa.worldwind.geom.Position
import gov.nasa.worldwind.geom.Angle
import gov.nasa.worldwind.globes.Globe
import gov.nasa.worldwind.view.orbit.OrbitView
import gov.nasa.worldwind.view.orbit.OrbitViewInputHandler

import groovy.util.slurpersupport.GPathResult
import java.beans.PropertyChangeListener
import java.net.URL
import java.net.URLEncoder
import net.yajjl.JsonParser
import net.yajjl.JsonParser.*

import twitter4j.*

class Twittersphere2Controller {
    // these will be injected by Griffon
    def model
    def view
		def twitter

    // void mvcGroupInit(Map args) {
    //    // this method is called after model and view are injected
    // }

    // void mvcGroupDestroy() {
    //    // this method is called when the group is destroyed
    // }


    def onStartupEnd = {
				 gov.nasa.worldwind.WorldWind.setOfflineMode(true)

        //def trends = getTrends()
        //println trends
        edt {
            model.searchTermsList.add("#JavaOne")
            getTrends()
            //model.searchTermsList.addAll(trends)
            model.addPropertyChangeListener('tweetList',
                { view.tweetListAnimator.restart()} as PropertyChangeListener)
        }
    }

    def search = {
        def mode = model.searchMode
        def text = model.searchText
        model.searching = true
        doOutside {
            def newTweets = []
            try {
                switch (mode) {
                    case 'Public':
                        newTweets = getPublicResults()
                        break
                    case 'Search' :
                        if (text == null || "".equals(text?.trim())) {
                            edt {
                                view.searchBox.setSelectedItem("Enter a search term")
                            }
                            return
                        }
                        newTweets = getSearchResults(text)
                        break

                    default:
                    5.times {
                        newTweets << [
                            pos:Position.fromDegrees(new Random().nextInt(180)-90, new Random().nextInt(360)-180, 0),
                            icon:view.imageIcon('/griffon-icon-48x48.png').image,
                            tweet: "Tweet! - $mode - $text",
                            user: 'Nobody'
                        ]
                    }

                }
            } finally {
                edt {
                    model.tweetListPos = 0
                    model.tweetList = newTweets
                    model.searching = false
                }
            }
        }
    }

    def getPublicResults() {
        List results = []
        def doc = slurpAPIStream("http://twitter.com/statuses/public_timeline.xml")
        doc=model.result //temporary add 
        doc.status.each {
            results << [
                icon: it.user.profile_image_url as String,
                tweet: it.text as String,
                user: it.user.screen_name as String
            ]
        }
        return results
    }

    def getSearchResults(search) {
        List results = []
        def url = "http://search.twitter.com/search.atom?q=${URLEncoder.encode(search)}&rpp=${model.searchLimit}"
        //println "url="+url
        def doc = slurpAPIStream(url)
        doc=model.result //temporary add 
        //println "doc="+doc?.dump()
        doc.entry.each {
            results << [
                icon: it.link[1]["@href"] as String,
                tweet: it.title as String,
                user: (it.author.uri as String)[19..-1]
            ]
        }
        return results
    }

    def addLocations(List<Map> tweets) {
        tweets.each this.&addLocation
    }

    def addLocation(def tweet) {
        try {
            def twitterUser = slurpAPIStream("http://twitter.com/users/show.xml?screen_name=$tweet.user")
            twitterUser=model.result //temporary add 
            def gnm = slurpAPIStream("http://ws.geonames.org/search?maxRows=1&q=${URLEncoder.encode(twitterUser.location as String)}")
            gnm=model.result //temporary add 
            if (gnm.geoname.size()) {
                tweet.pos = Position.fromDegrees(
                    Float.parseFloat(gnm.geoname[0].lat as String),
                    Float.parseFloat(gnm.geoname[0].lng as String),
                    0);
            }
        } catch (Exception ignore) {}
    }

    def nextTweet = {
        if (model.tweetList.size() > model.tweetListPos) {
            def tweet = model.tweetList[model.tweetListPos++]
            doOutside {
                if (!tweet.pos) {
                    addLocation(tweet)
                }
                if (tweet.icon instanceof String) {
                    tweet.icon = view.imageIcon(url:new URL(tweet.icon)).image

                }
                if (tweet.pos) {
                    edt {
                        view.addTweet(tweet.pos, tweet.user, tweet.tweet, tweet.icon)
                        if (model.animate) {
                            OrbitView orbitView = view.wwd.view
														
														//see http://forum.worldwindcentral.com/showthread.php?t=24345
														OrbitViewInputHandler ovih = (OrbitViewInputHandler) orbitView.getViewInputHandler()
														ovih.addPanToAnimator(tweet.pos,Angle.ZERO, Angle.ZERO, 1000000)

                            //orbitView.applyStateIterator(FlyToOrbitViewStateIterator.createPanToIterator(
                            //    orbitView, view.wwd.model.globe, tweet.pos,
                            //     Angle.ZERO, Angle.ZERO, 1000000))
                        } else {
                            doLater nextTweet
                        }
                    }
                } else {
                    edt nextTweet
                }
            }
        } else {
            view.tweetListAnimator.stop();
        }

    }

    def prevTweet = {

    }
    //
    //see http://groups.google.com/group/twitter-api-announce/browse_thread/thread/bec060db85d8cf72
    //
    def getTrends = {
				//println twitter.dump()
        def jsonText
        try {
            def parser = new JsonParser()
            jsonText = new URL("http://api.twitter.com/1/trends.json").openStream().text
            def obj = parser.parseObject(jsonText)
            def trendNames = obj.trends.collect{it.name}
/*
						//twitterF = new TwitterFactory().instance
						//trends = twitterF.getLocationTrends(23424856).trends
						trends = twitter.getLocationTrends(23424856).trends
						println "trends ="+ trends.dump()
						def trendNames = trends.collect{it.name}
						println "trendNames="+ trendNames.dump()
            //return trendNames[0..4]
*/
            model.searchTermsList.addAll(trendNames[0..4])
        } catch(Exception e) {
            System.err.println jsonText
            throw e
        }
    }

    XmlSlurper slurper = new XmlSlurper()
    GPathResult slurpAPIStream(String url) {
        def text = ""
        try {
            text = new URL(url).openStream().text
            //println "text="+text.dump()
            synchronized (slurper) {
                def result=slurper.parse(new StringReader(text))
                //println "result="+result.dump()
                model.result = result
                return result
            }
        } catch (Exception e) {
            System.err.println text
            throw e
        }
    }
}
