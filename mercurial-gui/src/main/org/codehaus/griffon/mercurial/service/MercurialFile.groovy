package org.codehaus.griffon.mercurial.service

public class MercurialFile {
    String name
    String status
    String directory
    String toString(){
        "[($status) - ($name) - ($directory)]"
    }
}
