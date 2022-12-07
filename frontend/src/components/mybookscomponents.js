import React from 'react';
import '../css/mybooks.css';
import { Searchbar, Timeseacherbar } from './searchbarcomponents';
import { Details } from './detailscomponents';
import { NameList } from './namelistcomponents';

export class Mybookscontainer extends React.Component{

    data=[];
    constructor(props){
        super(props);
        // this.data=props.data;
        this.state={
            alldata:props.data,
            nowalldata:props.data,
            nowdata:[],
            nowselecteddata:[-1,"","Bookname","BookAuther","BookPrice","https://i-1-lanrentuku.52tup.com/2020/12/29/f3eab929-d990-42d9-b858-0270ae7528b6.jpg?imageView2/2/w/1024/","Click button left to select a book.",0,0],
            issorted:false,
            isdecending:false
        }
    }
    componentWillReceiveProps(nextProps){
        this.setState({
            alldata:nextProps.data,
            nowalldata:nextProps.data,
            nowdata:[],
            nowselecteddata:[-1,"","Bookname","BookAuther","BookPrice","https://i-1-lanrentuku.52tup.com/2020/12/29/f3eab929-d990-42d9-b858-0270ae7528b6.jpg?imageView2/2/w/1024/","Click button left to select a book.",0,0],
            issorted:false,
            isdecending:false
        })
    }
    Showdetails(e){
        var namelist=document.getElementsByClassName("nameelement");
        for (let i=0;i<namelist.length;i++){
            namelist[i].style.background="rgba(255,255,255,0)";
        }
        document.getElementById(e.currentTarget.id).style.background="rgba(255,255,255,0.3)";
        var bookid=e.currentTarget.id.substring(3);
        for (let i=0;i<this.state.nowdata.length;i++){
            if(this.state.nowdata[i][0]===bookid){
                this.setState({
                    nowselecteddata:this.state.nowdata[i]
                })
            }
        }
    }
    datasort(e){
        if (this.state.issorted){
            if(this.state.isdecending){
                e.currentTarget.children[0].innerHTML="Name";
                this.setState({
                    issorted:false,
                    isdecending:false,
                    // nowdata:this.data
                })
            }else{
                e.currentTarget.children[0].innerHTML="Name\u2191";
                var newdata=this.state.nowdata.slice();
                newdata.sort(function(a,b){
                    return a[2]>b[2]?-1:1;
                });
                this.setState({
                    isdecending:true,
                    nowdata:newdata
                })
            }
        }else{
            e.currentTarget.children[0].innerHTML="Name\u2193";
            var newdata=this.state.nowdata.slice();
            newdata.sort(function(a,b){
                return b[2]>a[2]?-1:1;
            });
                this.setState({
                    issorted:true,
                    nowdata:newdata
                })
        }
        
    }
    readit(e){
        if(this.state.nowselecteddata[0]===-1) return;
        var bookname=document.getElementById("bookdetails").children[1].children[1].innerHTML;
        alert("Reading "+bookname+".");
    }

    searchdata=()=>{
        var searchwhat=document.getElementById("searchdata").value;
        var newdata=[];
        for (let i=0;i<this.state.alldata.length;i++){
            if(this.state.alldata[i][2].search(searchwhat)!==-1){
                newdata.push(this.state.alldata[i]);
            }
        }
        this.setState({
            nowalldata:newdata,
            nowdata:[],
        })
    }

    timesearchdata=(starttime,endtime)=>{
        var newdata=[];
        for (let i=0;i<this.state.alldata.length;i++){
            if(this.state.alldata[i][9]>=starttime&&this.state.alldata[i][9]<=endtime){
                newdata.push(this.state.alldata[i]);
            }
        }
        this.setState({
            nowalldata:newdata,
            nowdata:[],
        })
    }

    SelectOrderId=(e)=>{
        let orderid=e.currentTarget.children[0].innerHTML;
        let newnowdata=[];
        for (let i=0;i<this.state.nowalldata.length;i++)
        {
            if(this.state.nowalldata[i][10]===orderid)
            {
                newnowdata.push(this.state.nowalldata[i]);
            }
        }
        this.setState({
            nowdata:newnowdata,
        })
    }

    updatealldata(){
        this.props.parent.updatealldata();
    }
    render(){
        let totprice=0;
        for (let i=0;i<this.state.nowdata.length;i++)
        {
            totprice=totprice+parseFloat(this.state.nowdata[i][4]);
        }
        return(
            <div className="mybookscontainer">
                <div className="card">
                    <NameList ele={this.state.nowalldata} parent={this} options="books_orders"></NameList>
                    <NameList ele={this.state.nowdata} parent={this} options="books"></NameList>
                    <Details nowselecteddata={this.state.nowselecteddata} parent={this} options={"mybooks"}></Details>
                </div>
                <Searchbar parent={this}></Searchbar>
                <Timeseacherbar parent={this}></Timeseacherbar>
                <div className="statisticsbar">Totprice:{totprice.toFixed(2)} dollars  Totnum:{this.state.nowdata.length}</div>
            </div>
        )
    }
}

