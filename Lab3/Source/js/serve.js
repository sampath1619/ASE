/**
 * Created by gattu on 2/8/2017.
 */
var express = require('express');
var app = express();
var request = require('request');
app.get('/gd/:id', function (req, res) {
    var result = {
        'body': []
    };

    request('https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=36bd422defaf495f500542567ea0311b&text=' + req.params.search + '&format=json&nojsoncallback=1', function (error, response, body)
    {


        body0 = JSON.parse(body);

        co1 = body0.photos.photo[0];
        var url = 'https://farm'+ co1.farm +'.staticflickr.com/'+ co1.server +'/'+co1.id+'_'+co1.secret +'.jpg';
// return console.log(url);
        request('http://apius.faceplusplus.com/v2/detection/detect?api_key=28b0bb3c15498c2a50c121ef734bf6f0&api_secret=jERvGC5mZKzPO-fsUaRgzT2jAffarSEb%20&url=' + 'http://www.forcelebrities.com/wp-content/uploads/2016/03/mahendra-singh-dhoni-photos-in-black-goggles-8315.jpeg' + '&attribute=age,gender', function (error, response, body) {
            if (error) {
                return console.log('Error:', error);}

            if (response.statusCode !== 200) {
                return console.log('Invalid Status Code Returned:', response.statusCode);}

            data= JSON.parse(body);
            data1=data.face[0].attribute;
            result0 = data1.age.value;
            result1 = data1.gender.value;
            result.body.push({"url": url, "age":result0 , "gender": result1});

            res.contentType('application/json');
            res.write(JSON.stringify(result));
            res.end();
        })
    })
})
var server = app.listen(8081, function () {
    var host = server.address().address
    var port = server.address().port
    console.log("Example app listening at http://%s:%s", host, port)
})
