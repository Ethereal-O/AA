import React from 'react';
import '../css/allbooks.css';
import { Searchbar } from './searchbarcomponents';
import { Details } from './detailscomponents';
import { NameList } from './namelistcomponents';
import { CategoryList } from './categorylistcomponents';
import { checklogin, addcart, admindeletebook, adminaddbook, searchbookdata, getbookauthor, searchbooktype } from './connect_to_backend';

export class Allbookscontainer extends React.Component {

    data = [];
    constructor(props) {
        super(props);
        // this.data=props.data;
        this.state = {
            alldata: props.data,
            nowdata: props.data,
            nowcatedata: props.data,
            nowselecteddata: [-1, "", "Bookname", "BookAuther", "BookPrice", "https://i-1-lanrentuku.52tup.com/2020/12/29/f3eab929-d990-42d9-b858-0270ae7528b6.jpg?imageView2/2/w/1024/", "Click button left to select a book.", 0, 0],
            issorted: false,
            isdecending: false
        }
    }
    componentWillReceiveProps(nextProps) {
        this.setState({
            alldata: nextProps.data,
            nowdata: nextProps.data,
            nowcatedata: nextProps.data,
            nowselecteddata: [-1, "", "Bookname", "BookAuther", "BookPrice", "https://i-1-lanrentuku.52tup.com/2020/12/29/f3eab929-d990-42d9-b858-0270ae7528b6.jpg?imageView2/2/w/1024/", "Click button left to select a book.", 0, 0],
            issorted: false,
            isdecending: false
        })
    }
    selectcategory(e) {
        var namelist = document.getElementsByClassName("categoryelement");
        for (let i = 0; i < namelist.length; i++) {
            namelist[i].style.background = "rgba(255,255,255,0)";
        }
        document.getElementById(e.currentTarget.id).style.background = "rgba(255,255,255,0.3)";
        // var cateid=e.currentTarget.id.substring(7);
        var cateid = e.currentTarget.children[0].innerHTML;
        var newdata = [];
        for (let i = 0; i < this.state.alldata.length; i++) {
            if (this.state.alldata[i][1] === cateid || cateid === "") {
                newdata.push(this.state.alldata[i]);
            }
        }
        this.setState({
            nowdata: newdata,
            nowcatedata: newdata
        })
    }
    Showdetails(e) {
        var namelist = document.getElementsByClassName("nameelement");
        for (let i = 0; i < namelist.length; i++) {
            namelist[i].style.background = "rgba(255,255,255,0)";
        }
        document.getElementById(e.currentTarget.id).style.background = "rgba(255,255,255,0.3)";
        var bookid = e.currentTarget.id.substring(3);
        for (let i = 0; i < this.state.nowdata.length; i++) {
            if (this.state.nowdata[i][0] === bookid) {
                this.setState({
                    nowselecteddata: this.state.nowdata[i]
                })
            }
        }
    }
    datasort(e) {
        if (this.state.issorted) {
            if (this.state.isdecending) {
                document.getElementById(e.currentTarget.id).children[0].innerHTML = "name";
                this.setState({
                    issorted: false,
                    isdecending: false,
                    // nowdata:this.data
                })
            } else {
                document.getElementById(e.currentTarget.id).children[0].innerHTML = "name\u2191";
                var newdata = this.state.nowdata.slice();
                newdata.sort(function (a, b) {
                    return a[2] > b[2] ? -1 : 1;
                });
                this.setState({
                    isdecending: true,
                    nowdata: newdata
                })
            }
        } else {
            document.getElementById(e.currentTarget.id).children[0].innerHTML = "name\u2193";
            var newdata = this.state.nowdata.slice();
            newdata.sort(function (a, b) {
                return b[2] > a[2] ? -1 : 1;
            });
            this.setState({
                issorted: true,
                nowdata: newdata
            })
        }

    }
    buyclick(e) {
        if (this.state.nowselecteddata[0] === -1) return;
        if (localStorage.getItem("isloged") !== "true") {
            alert("Please login first!");
            return;
        }
        var b = document.getElementById('addcartcontent');
        b.children[0].children[0].innerHTML = "The book you will add to your cart is: " + this.state.nowselecteddata[2];
        localStorage.setItem("buybookid", this.state.nowselecteddata[0]);
        if (b.style.visibility === 'hidden') {
            b.style.visibility = 'visible';
        } else {
            b.style.visibility = 'hidden';
        }
    }
    searchdata = () => {
        var searchwhat = document.getElementById("searchdata").value;
        var searchby = document.getElementById("searchby").value;

        if (searchby === "type") {
            // *******************************
            // for search book type
            // *******************************

            let searchbooktypecallback = (data) => {
                console.log(data);
                this.setState({
                    nowdata: data
                })
            }

            searchbooktype(searchwhat, searchbooktypecallback);
        }

        if (searchby === "name") {
            // *******************************
            // for search book name
            // *******************************
            var newdata = [];
            for (let i = 0; i < this.state.nowcatedata.length; i++) {
                if (this.state.nowcatedata[i][2].search(searchwhat) !== -1) {
                    newdata.push(this.state.nowcatedata[i]);
                }
            }

            this.setState({
                nowdata: newdata
            })
        }

        if (searchby === "description") {
            // *******************************
            // for search book info
            // *******************************

            let searchbookdatacallback = (data) => {
                console.log(data);
                this.setState({
                    nowdata: data
                })
            }

            searchbookdata(searchwhat, searchbookdatacallback);
        }

        // *******************************
        // for get book author
        // *******************************


        // let getbookauthorcallback = (data) => {
        //     console.log(data);
        //     alert("The author of the book is "+data);
        //     // document.getElementById("header").innerHTML=data;
        // }

        // getbookauthor(searchwhat, getbookauthorcallback);

    }

    deletebook = (e, bookid) => {
        let admindeletebookcallback = (susflag) => {
            if (susflag > 0) {
                alert("Delete Success!");
                this.updatealldata();
            } else {
                alert("Delete false!");
            }
        }
        admindeletebook(bookid, admindeletebookcallback);
    }

    addbook = (e, booktype) => {
        let adminaddbookcallback = (susflag) => {
            if (susflag > 0) {
                alert("Add Success!");
                this.updatealldata();
            } else {
                alert("Add false!");
            }
        }
        adminaddbook(booktype, adminaddbookcallback);
    }

    updatealldata() {
        this.props.parent.updatealldata();
    }

    render() {
        return (
            <div className="allbookscontainer">
                <div className="card">
                    <CategoryList parent={this}></CategoryList>
                    <NameList ele={this.state.nowdata} parent={this} options="books"></NameList>
                    <Details nowselecteddata={this.state.nowselecteddata} parent={this} options={"allbooks"}></Details>
                </div>
                <select id='searchby'>
                    <option value="type">by type</option>
                    <option value="name">by name</option>
                    <option value="description">by description</option>
                </select>
                <Searchbar parent={this}></Searchbar>
            </div>
        )
    }
}

export class Bcontents extends React.Component {
    constructor() {
        super();
        this.state = {
            password: "",
        }
    }

    changepassword = (e) => {
        this.setState({
            password: e.target.value
        })
    }

    tryaddcart = () => {
        let checklogincallback = (susflag) => {
            let addcartcallback = (addcartsusflag) => {
                if (addcartsusflag > 0) {
                    document.getElementById('addcartcontent').style.visibility = 'hidden';
                    alert("Success!");
                } else {
                    if (addcartsusflag === 0) {
                        document.getElementById('addcartcontent').style.visibility = 'hidden';
                        alert("Duplicated book!")
                    } else {
                        alert("Something wrong!");
                    }
                }
            }
            if (susflag > 0) {
                addcart(localStorage.getItem("buybookid"), localStorage.getItem("username"), addcartcallback);
            } else {
                alert("Wrong password!");
            }
        }
        if (this.state.password === "") {
            alert("blank password detected!");
            return;
        }
        checklogin(localStorage.getItem("username"), this.state.password, checklogincallback);
        document.getElementById("allbookcpassword").value = "";
        this.setState({
            password: ""
        })
    }

    render() {
        return (
            <div className="buycontent" id="addcartcontent">
                <form className="word" name="input" action="" method="get">
                    <p>The book you will add to your cart is:</p>
                    <br />
                    <div className="inputbox">
                        <div className="display">Password:</div>
                        <input onChange={(e) => this.changepassword(e)} className="typein" type="password" name="cpassword" id="allbookcpassword" />
                    </div>
                    <br />
                    <input onClick={() => this.tryaddcart()} className="buybutton" type="button" value="Submit" />
                </form>
            </div>
        )
    }
}
