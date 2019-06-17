<!DOCTYPE HTML>
<html>

    <head>
        <title>Offline Finger Matching</title>
        <meta name="description" content="website description" />
        <meta name="keywords" content="website keywords, website keywords" />
        <meta http-equiv="content-type" content="text/html; charset=windows-1252" />
        <link rel="stylesheet" type="text/css" href="style/style.css" title="style" />
    </head>

    <body>
        <div id="main">
            <div id="header">
                <div id="logo">
                    <div id="logo_text">
                        <!-- class="logo_colour", allows you to change the colour of the text -->
                        <h1><a href="index.html">OFFLINE<span style="color: red">FINGER MATCHING</span></a></h1>
                    </div>
                </div>
                <div id="menubar">
                    <ul id="menu">
                        <!-- put class="selected" in the li tag for the selected page - to highlight which page you're on -->
                        <li class="selected"><a href="home.jsp">Home</a></li>
                        <li><a href="/FingerOfflineMatch/FingerCheck">Offline Match</a></li>
                        <li><a href="Finger_Matching.jsp">Finger Matching</a></li>
                        <li><a href="DatabaseInfo.jsp">Database Information</a></li>
                        <li><a href="index.jsp">Sign Out</a></li>
                    </ul>
                </div>
            </div>
            <div id="site_content">

                <div id="content" >
                    <!-- Agent Type -->
                    <form style="margin-left: 42px;" action="/FingerOfflineMatch/FingerCheck">
                        <div style = "background-color: white; width: 200px; float: left; ">

                            <label>Select Customer Type :</label><br><br>

                            <label>Agent Point :</label><br>
                            <input type="text" name="agentPoint" id="agentPoint" placeholder="Specific Point No">
                            <br>
                            <label>Customer No:</label><br>
                            <input type="text" name="custNo"  id="custNo" placeholder="Specific Cust No"><br><br>
                            <label>All Customer<label>
                                    <input type="checkbox" name="allCust"  value="all"><br><br>
                                    </div>

                                    <!-- Outlet -->
                                    <div style="background-color: white; width: 200px; float: left; margin-left: 68px;">

                                        <label>Matching User Type :</label><br><br>
                                        <br>

                                        <input type="radio" name="userType" value="agent"> Agent<br>
                                        <input type="radio" name="userType" value="aro"> ARO<br>
                                        <input type="radio" name="userType" value="all"> All<br> 
                                        <br><br>
                                        <input type="submit" value="Start Matching..." style="margin-bottom: 10px; color: white; background-color: green; ">
                                    </div>
                    </form>

                                    </div>
                                    </div>
                                    <div id="footer">
                                        Copyright &copy; mdroman601@gmail.com | <a href="http://validator.w3.org/check?uri=referer">Era InfoTech Ltd.</a>
                                    </div>
        </div>
    </body>
</html>
