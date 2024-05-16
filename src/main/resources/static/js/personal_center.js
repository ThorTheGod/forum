//点击标签打开对应内容（帖子/回复/评论/收藏）
//     function openTab(evt, tabName)
//     {
//         var i,tabcontent,tablinks;
//         tabcontent = document.getElementsByClassName("tabcontent");
//         for(i = 0; i < tabcontent.length; i++)
//         {
//             tabcontent[i].style.display = "none";
//         }
//         tablinks = document.getElementsByClassName("tablinks");
//        for(i = 0; i < tablinks.length; i++)
//         {
//             tablinks[i].className = tablinks[i].className.replace("active","");
//         }
//             document.getElementById(tabName).style.display = "block";
//             evt.currentTarget.className += "active";
//     }

//触发id="default"click事件
//     document.getElementById("default").click();

//点击回复按钮，调出回复编写框
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

//点击修改评论按钮，调出编写框
    function modifyShow(a) 
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