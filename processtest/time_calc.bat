::詳しい説明はこちら
:: timeitコマンドでアプリケーションの実行時間を測定する
::http://www.atmarkit.co.jp/fwin2k/win2ktips/422timecmd/timecmd.html


::Version Number:   Windows NT 5.1 (Build 2600) …OSバージョン
::Exit Time:        11:38 am, Friday, May 7 2004 …終了時間
::Elapsed Time:     0:01:00.234 …開始から終了までの経過時間
::Process Time:     0:00:19.968 …処理時間
::System Calls:     902230 …システム・コールの回数
::Context Switches: 36798 …コンテキスト切り替えの回数
::Page Faults:      84248 …ページ・フォルトの回数
::Bytes Read:       481545 …ディスクからの読み出しバイト数
::Bytes Written:    252408 …ディスクへの書き込みバイト数
::Bytes Other:      524120 …そのほかのデータ転送バイト数

::.\timeit\timeit.exe run.bat > .\logs\timeit.log 2>&1

.\timeit\timeit.exe run.bat
pause
