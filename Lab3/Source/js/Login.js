    /**
     * Created by gattu on 2/6/2017.
     */

/*var valid = false;
function validate() {
    var msg = "";
    var uname = document.myForm.username.value;
    var pwd = document.myForm.pwd.value;
    if (uname.length == 0 && pwd.length == 0)
        msg = msg + "Please enter username and password \n";
    else if (pwd.length == 0)
        msg = msg + "Please enter password \n";
    else if (uname.length == 0)
        msg = msg + "Please enter username \n";
    if (msg.length > 0) {
        window.alert(msg);
        valid = false;
    }
    else
        valid = true;
    return valid;
}
function registration() {
    var msg = "";
    var fname = document.regmyForm.fname.value;
    var lname = document.regmyForm.lname.value;
    var email = document.regmyForm.email.value;
    var user = document.regmyForm.username.value;
    var pwd = document.regmyForm.pwd.value;
    var repwd = document.regmyForm.repwd.value;
    if (fname.length == 0 && lname.length == 0 && email.length == 0 && user.length == 0 && pwd.length == 0 && repwd.length == 0)
        msg = msg + "Please enter all the fields to have successful registration \n";
    if (msg.length > 0) {
        window.alert(msg);
        valid = false;
    }
    else
        valid = true;
    return valid;
}
function logInAct() {
    if (document.getElementById("username").value.length > 0 && document.getElementById("password").value.length > 0) {
        window.location.href = 'home.html';
    }
    else
        alert("Give all details");
}
window.fbAsyncInit = function () {
    FB.init({
        appId: '1415062328510630',
        xfbml: true,
        version: 'v2.7'
    });
};
(function (d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {
        return;
    }
    js = d.createElement(s);
    js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
function tLogin() {
    FB.login(function (response) {
            if (response.authResponse) {
                // document.getElementById('login').style.visibility=false;
                getInfo();
            } else {
                console.log('User cancelled login or did not fully authorize.');
            }
        }, {scope: 'email'}
    );
}
function getInfo() {
    FB.api('/me', function (response) {
            console.log(response.name.toString());
            var str = "<b>Name</b> : " + response.name + "<br>";
            str += "<b>Link: </b>" + response.link + "<br>";
            str += "<b>Username:</b> " + response.username + "<br>";
            str += "<b>id: </b>" + response.id + "<br>";
            str += "<b>Email:</b> " + response.email + "<br>";
            str += "<input type='button' value='Get Photo' onclick='getPhoto();'/>";
            str += "<input type='button' value='Logout' onclick='Logout();'/>";
            document.getElementById('status').innerHTML = "Connected";
            var name = response.name;
            console.log(name)
            localStorage.setItem("Name", name);
        }
    );
    //document.getElementById('home').innerHTML =  document.getElementById('status').value;
    window.location.href = 'home.html';
}
function navigate() {
    var str = document.getElementById('status').value;
    document.getElementById('home').innerHTML = str.toString();
    window.location.href = 'home.html';
}
function Logout() {
    FB.logout(function () {
        document.location.reload();
    });
}
*/
    var googleUser = {};
    var startApp = function () {
        gapi.load('auth2', function () {
            // Retrieve the singleton for the GoogleAuth library and set up the client.
            auth2 = gapi.auth2.init({
                client_id: '434544707673-h8q0vltso0udlnakg2s0on4dk55shq6m.apps.googleusercontent.com',
                client_secret: '5QEHqS15D-sj11qbAMS3Te8c',
                cookiepolicy: 'single_host_origin',
                // Request scopes in addition to 'profile' and 'email'
                //scope: 'additional_scope'
            });
            attachSignin(document.getElementById('customBtn'));
        });
    };
    function attachSignin(element) {
        console.log(element.id);
        auth2.attachClickHandler(element, {},
            function (googleUser) {
                document.getElementById('name').innerText = "Signed in: " +
                    googleUser.getBasicProfile().getName();
            }, function (error) {
                alert(JSON.stringify(error, undefined, 2));
            });
    }

    var valid = false;
    function validate() {
        var msg = "";
        var uname = document.myForm.username.value;
        var pwd = document.myForm.pwd.value;
        if (uname.length == 0 && pwd.length == 0)
            msg = msg + "Please enter username and password \n";
        else if (pwd.length == 0)
            msg = msg + "Please enter password \n";
        else if (uname.length == 0)
            msg = msg + "Please enter username \n";
        if (msg.length > 0) {
            window.alert(msg);
            valid = false;
        }
        else
            valid = true;
        return valid;
    }
    function registration() {
        var msg = "";
        var fname = document.regmyForm.fname.value;
        var lname = document.regmyForm.lname.value;
        var email = document.regmyForm.email.value;
        var user = document.regmyForm.username.value;
        var pwd = document.regmyForm.pwd.value;
        var repwd = document.regmyForm.repwd.value;
        if (fname.length == 0 && lname.length == 0 && email.length == 0 && user.length == 0 && pwd.length == 0 && repwd.length == 0)
            msg = msg + "Please enter all the fields to have successful registration \n";
        if (msg.length > 0) {
            window.alert(msg);
            valid = false;
        }
        else
            valid = true;
        return valid;
    }
    function logInAct() {
        if (document.getElementById("username").value.length > 0 && document.getElementById("password").value.length > 0) {
            window.location.href = 'home.html';
        }
        else
            alert("Give all details");
    }
    window.fbAsyncInit = function() {
        FB.init({
            appId      : '719448548222315',
            cookie     : true,
            xfbml      : true,
            version    : 'v2.8'
        });
        FB.AppEvents.logPageView();
    };

    (function(d, s, id){
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) {return;}
        js = d.createElement(s); js.id = id;
        js.src = "//connect.facebook.net/en_US/sdk.js";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));
    function tLogin() {
        FB.login(function (response) {
                if (response.authResponse) {
                    // document.getElementById('login').style.visibility=false;
                    getInfo();
                } else {
                    console.log('User cancelled login or did not fully authorize.');
                }
            }, {scope: 'email'}
        );
    }
    function getInfo() {
        FB.api('/me', function (response) {
                console.log(response.name.toString());
                var str = "<b>Name</b> : " + response.name + "<br>";
                str += "<b>Link: </b>" + response.link + "<br>";
                str += "<b>Username:</b> " + response.username + "<br>";
                str += "<b>id: </b>" + response.id + "<br>";
                str += "<b>Email:</b> " + response.email + "<br>";
                str += "<input type='button' value='Get Photo' onclick='getPhoto();'/>";
                str += "<input type='button' value='Logout' onclick='Logout();'/>";
                document.getElementById('status').innerHTML = "Connected";
                var name = response.name;
                console.log(name)
                localStorage.setItem("Name", name);
            }
        );
        //document.getElementById('home').innerHTML =  document.getElementById('status').value;
        window.location.href = 'home.html';
    }
    function navigate() {
        var str = document.getElementById('status').value;
        document.getElementById('home').innerHTML = str.toString();
        window.location.href = 'home.html';
    }
    function Logout() {
        FB.logout(function () {
            document.location.reload();
        });
    }