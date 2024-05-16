//点赞、收藏等点击变换效果
var praise1 = document.getElementById("post_praise");
// var praise2 = document.getElementById("post_praise2");
// var bad1 = document.getElementById("post_bad");
// var bad2 = document.getElementById("post_bad2");
// var collect1 = document.getElementById("post_collect");
// var collect2 = document.getElementById("post_collect2");
// var flag = true;
//
// function praise(){
//     if(flag == true)
//     {
//         praise1.style.display = "none";
//         praise2.style.display = "block";
//         flag = false;
//     }
//     else
//     {
//         praise2.style.display = "none";
//         praise1.style.display = "block";
//         flag = true;
//     }
// }
//
// function bad(){
//     if(flag == true)
//     {
//         bad1.style.display = "none";
//         bad2.style.display = "block";
//         flag = false;
//     }
//     else
//     {
//         bad2.style.display = "none";
//         bad1.style.display = "block";
//         flag = true;
//     }
// }
//
// function collect(){
//     if(flag == true)
//     {
//         collect1.style.display = "none";
//         collect2.style.display = "block";
//         flag = false;
//     }
//     else
//     {
//         collect2.style.display = "none";
//         collect1.style.display = "block";
//         flag = true;
//     }
// }

//回复编写框
function replyShow(a) 
{
    if(document.getElementById(a).style.display !== "block")
    {
        document.getElementById(a).style.display = "block";
    }
    else
    {
        document.getElementById(a).style.display = "none";
    }
}