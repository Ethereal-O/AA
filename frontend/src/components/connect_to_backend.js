import * as Orderservice from "../utils/orderService";

//home
export function trylogin(username,password,checklogincallback) // 返回2管理员，1普通用户，0密码错误，-1无用户
{
    let checkdata=new FormData();
    checkdata.append("username",username);
    checkdata.append("password",password);
    Orderservice.tryloginservice(checkdata, checklogincallback);
}

export function trylogout(logoutcallback)
{
    Orderservice.trylogoutservice(logoutcallback);
}

export function checklogin(username,password,checklogincallback) // 返回2管理员，1普通用户，0密码错误，-1无用户
{
    let checkdata=new FormData();
    checkdata.append("username",username);
    checkdata.append("password",password);
    Orderservice.checkloginservice(checkdata, checklogincallback);
}

export function register(username,password,email,registercallback) // 返回1普通用户，0已注册，-1失败
{
    let registerdata=new FormData();
    registerdata.append("username",username);
    registerdata.append("password",password);
    registerdata.append("email",email);
    Orderservice.registerservice(registerdata,registercallback);
}

// allbook
export function getallbookdata(getallbookdatacallback)
{
    // let data=[[0,"HP","HP and PS","J.K.RowLing","100","hp_and_ps","Harry Potter and the Philosopher Stone Sorcerer's Stone."],[1,"HP","HP and GF","J.K.RowLing","100","hp_and_gf","Harry Potter and the Goblet of Fire."],[2,"CS","CSAPP","Randal E.Bryant / David O'Hallaron","200","csapp","Computer Systems: A Programmer's Perspective."]];
    // return data;
    Orderservice.getallbookdataservice(getallbookdatacallback);
}

export function searchbookdata(keyword, searchbookdatacallback)
{
    let list=new FormData();
    list.append("keyword",keyword);
    Orderservice.searchbookdataservice(list, searchbookdatacallback);
}

export function getbookauthor(bookname, getbookauthorcallback)
{
    let list=new FormData();
    list.append("bookname",bookname);
    Orderservice.getbookauthorservice(list, getbookauthorcallback);
}

export function addcart(bookid,username,addcartcallback)
{
    if (bookid===-1) return;
    // return 1;
    let addcartdata=new FormData();
    addcartdata.append("bookid",bookid);
    addcartdata.append("username",username);
    Orderservice.addcartservice(addcartdata,addcartcallback);
}

export function adminchangedata(index, options,content,adminchangedatacallback)
{
    if (index===-1) return;
    // return 1;
    let admindata=new FormData();
    admindata.append("index",index);
    admindata.append("options",options);
    admindata.append("content",content);
    Orderservice.adminchangedataservice(admindata,adminchangedatacallback);
}

export function admindeletebook(bookid,admindeletebookcallback)
{
    // alert("Not implement!");
    if (bookid===-1) return;
    // return 1;
    let list=new FormData();
    list.append("bookid",bookid);
    Orderservice.admindeletebookservice(list,admindeletebookcallback);
}

export function adminaddbook(booktype,adminaddbookcallback)
{
    // alert("Not implement!");
    if (booktype==="") return;
    // return 1;
    let list=new FormData();
    list.append("booktype",booktype);
    Orderservice.adminaddbookservice(list,adminaddbookcallback);
}

// cart
export function getusercartdata(username,getusercartdatacallback)
{
    // let data=[[0,"HP","HP and PS","J.K.RowLing","100","hp_and_ps","Harry Potter and the Philosopher Stone Sorcerer's Stone."],[1,"HP","HP and GF","J.K.RowLing","100","hp_and_gf","Harry Potter and the Goblet of Fire."],[2,"CS","CSAPP","Randal E.Bryant / David O'Hallaron","200","csapp","Computer Systems: A Programmer's Perspective."]];
    // return data;
    let list=new FormData();
    list.append("username",username);
    Orderservice.getusercartdataservice(list,getusercartdatacallback);
}

export function userdeletecartdata(bookid,username,userdeletecartdatacallback)
{
    if (bookid===-1)return;
    // return 1;
    let deletedata=new FormData();
    deletedata.append("bookid",bookid);
    deletedata.append("username",username);
    Orderservice.userdeletecartdataservice(deletedata,userdeletecartdatacallback);
}

export function usercleancart(username,usercleancartcallback)
{
    // return 1;
    let list=new FormData();
    list.append("username",username);
    Orderservice.usercleancartservice(list,usercleancartcallback);
}

// shelf
export function getusershelfdata(username,getusershelfdatacallback)
{
    // let data=[[0,"HP","HP and PS","J.K.RowLing","100","hp_and_ps","Harry Potter and the Philosopher Stone Sorcerer's Stone."],[1,"HP","HP and GF","J.K.RowLing","100","hp_and_gf","Harry Potter and the Goblet of Fire."],[2,"CS","CSAPP","Randal E.Bryant / David O'Hallaron","200","csapp","Computer Systems: A Programmer's Perspective."]];
    // return data;
    let list=new FormData();
    list.append("username",username);
    Orderservice.getusershelfdataservice(list,getusershelfdatacallback);
}

// alluser
export function getalluserdata(getalluserdatacallback)
{
    // let data=[[0,"normaluser1","normaluser1",1],[1,"normaluser2","normaluser2",1],[3,"specialuser1","specialuser1",2]];
    // return data;
    Orderservice.getalluserdataservice(getalluserdatacallback);
}

export function adminchangeuserdata(id, index,content,adminchangeuserdatacallback)
{
    // if (index===-1) return;
    // return 1;
    let admindata=new FormData();
    admindata.append("id",id);
    admindata.append("index",index);
    admindata.append("content",content);
    Orderservice.adminchangeuserdataservice(admindata,adminchangeuserdatacallback);
}

export function admindeleteuserdata(id,admindeleteuserdatacallback)
{
    let list=new FormData();
    list.append("id",id);
    Orderservice.admindeleteuserdataservice(list,admindeleteuserdatacallback);
}

// allorder
export function getallorderdata(getalluserdatacallback)
{
    // let data=[["normaluser1",1,"normaluser1"],["normaluser2",2,"normaluser2"],["specialuser1",3,"specialuser1"]];
    // return data;
    Orderservice.getallorderdataservice(getalluserdatacallback);
}

export function adminaddorder(username,orderid,bookid,adminaddordercallback)
{
    let adminaddorderdata=new FormData();
    adminaddorderdata.append("username",username);
    adminaddorderdata.append("orderid",orderid);
    adminaddorderdata.append("bookid",bookid);
    Orderservice.adminaddorderservice(adminaddorderdata,adminaddordercallback);
}

export function admindeleteorder(orderid,bookid,admindeleteordercallback)
{
    let admindeleteorderdata=new FormData();
    admindeleteorderdata.append("orderid",orderid);
    admindeleteorderdata.append("bookid",bookid);
    Orderservice.admindeleteorderservice(admindeleteorderdata,admindeleteordercallback);
}

export function usercleancartWebSocket(username,usercleancartcallback)
{
    // return 1;
    return Orderservice.usercleancartWebSocketservice(username,usercleancartcallback);
}