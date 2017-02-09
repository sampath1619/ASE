/**
 * Created by gattu on 2/8/2017.
 */
/**
 * Created by gattu on 2/8/2017.
 */
var app = angular.module('faceplus', []);
app.controller('face', function($scope, $http) {
    $scope.genrate = function () {


var url=document.getElementById("search").value
        var handler =$http.get('https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=36bd422defaf495f500542567ea0311b&text=' + url + '&format=json&nojsoncallback=1');
        handler.success(function (body0)

        {


            co1 = body0.photos.photo[0];
            var url1 = 'https://farm'+ co1.farm +'.staticflickr.com/'+ co1.server +'/'+co1.id+'_'+co1.secret +'.jpg';
// return console.log(url);
           var handler1=$http.get('http://apius.faceplusplus.com/v2/detection/detect?api_key=28b0bb3c15498c2a50c121ef734bf6f0&api_secret=jERvGC5mZKzPO-fsUaRgzT2jAffarSEb%20&url=' + 'http://www.slate.com/content/dam/slate/blogs/moneybox/2015/08/16/donald_trump_on_immigration_build_border_fence_make_mexico_pay_for_it/483208412-real-estate-tycoon-donald-trump-flashes-the-thumbs-up.jpg.CROP.promo-xlarge2.jpg' + '&attribute=age,gender');
           // var handler1=$http.get('http://apius.faceplusplus.com/v2/detection/detect?api_key=28b0bb3c15498c2a50c121ef734bf6f0&api_secret=jERvGC5mZKzPO-fsUaRgzT2jAffarSEb%20&url=' + url1 + '&attribute=age,gender');

            handler1.success(function (data)

            {
                data1=data.face[0].attribute;
                $scope.res = data1.age.value;
                $scope.res1 = data1.gender.value;


                result.body.push({"url": url, "age":result0 , "gender": result1});

                res.contentType('application/json');
                res.write(JSON.stringify(result));
                res.end();
            })
        })


    }
});

function show_image(src, width, height, alt) {
    var img = document.createElement("img");
    img.src = src;
    img.width = width;
    img.height = height;
    img.alt = alt;

    // This next line will just add it to the <body> tag
    document.body.appendChild(img);
}


