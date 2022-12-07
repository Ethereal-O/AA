import React from "react";
import '../css/mybooks.css'
import '../css/allbooks.css'
import '../css/mycarts.css'
import '../css/allusers.css';

export class NameList extends React.Component{

    constructor(props){
        super(props);
        // this.data=props.data;
        this.state={
            changeid:-1,
            changeindex:-1
        }
    }
    componentWillReceiveProps(nextProps){
        this.setState({
            changeid:-1,
            changeindex:-1
        })
    }

    changedata=(id,index)=>
    {
        this.setState({
            changeid:id,
            changeindex:index
        })
    }

    render(){
        if (this.props.options==="books")
        {
            return(
                <div className="name" id="namelist">
                    <div className="nameelement" onClick={(e) => this.props.parent.datasort(e)} id="nametitle">
                        <h2>Name</h2>
                    </div>
                    {
                        this.props.ele.map(item => (
                            <button id={"btn" + item[0]} onClick={(e) => this.props.parent.Showdetails(e)} className="nameelement">
                                <p>{item[2]}</p>
                            </button>
                        ))
                    }
                </div>
            )
        }
        else if (this.props.options==="books_orders")
        {
            let allorders=[];
            for (let i=0;i<this.props.ele.length;i++)
            {
                let flag=false;
                for (let j=0;j<allorders.length;j++)
                {
                    if(allorders[j]===this.props.ele[i][10])
                    {
                        flag=true;
                        break;
                    }
                }
                if(!flag)
                    allorders.push(this.props.ele[i][10]);
            }
            return(
                <div className="name" id="namelist">
                    <div className="nameelement" id="nametitle">
                        <h2>Orders</h2>
                    </div>
                    {
                        allorders.map(item => (
                            <button onClick={(e) => this.props.parent.SelectOrderId(e)} className="nameelement">
                                <p>{item}</p>
                            </button>
                        ))
                    }
                </div>
            )
        }
        else if (this.props.options==="carts")
        {
            return(
                <div className="name" id="namelist">
                    <div className="nameandpricetitle" id="nametitle">
                        <h2>Name</h2><h2>Price</h2>
                    </div>
                    {
                        this.props.ele.map(item => (
                            <button id={"btn" + item[0]} onClick={(e) => this.props.parent.Showdetails(e)} className="nameandpriceelement">
                                <div className="nameandprice">
                                    <p>{item[2]}</p>
                                    <p>{item[4]} dollars</p>
                                </div>
                            </button>
                        ))
                    }
                </div>
            )
        }
        else if (this.props.options==="users")
        {
            return(
                <div className="name" id="namelist">
                    <div className="userinfotitle" id="userinfotitle">
                        <h2 onClick={(e)=>this.props.parent.datasort(e)}>Id</h2><h2 onClick={(e)=>this.props.parent.datasort(e)}>Username</h2><h2 onClick={(e)=>this.props.parent.datasort(e)}>Password</h2><h2 onClick={(e)=>this.props.parent.datasort(e)}>Type</h2><h2>Delete</h2>
                    </div>
                    {
                        this.props.ele.map(item => (
                            <button id={"btn" + item[0]} className="userinfoelement">
                                <div className="userinfo">
                                    <p >{item[0]}</p>
                                    {
                                        this.state.changeid===item[0]&&this.state.changeindex===1?
                                        <input defaultValue={item[1]} onKeyDown={(e)=>this.props.parent.changeuserdata(e,item[0],1)}></input>:<p onClick={()=>this.changedata(item[0],1)}>{item[1]}</p>
                                    }
                                    {
                                        this.state.changeid===item[0]&&this.state.changeindex===2?
                                        <input defaultValue={item[2]} onKeyDown={(e)=>this.props.parent.changeuserdata(e,item[0],2)}></input>:<p onClick={()=>this.changedata(item[0],2)}>{item[2]}</p>
                                    }
                                    {
                                        this.state.changeid===item[0]&&this.state.changeindex===3?
                                        <input defaultValue={item[3]} onKeyDown={(e)=>this.props.parent.changeuserdata(e,item[0],3)}></input>:<p onClick={()=>this.changedata(item[0],3)}>{item[3]}</p>
                                    }
                                    <p onClick={()=>this.props.parent.deleteuserdata(item[0])}>delete</p>
                                </div>
                            </button>
                        ))
                    }
                </div>
            )
        }
        else if (this.props.options==="orders_users")
        {
            return(
                <div className="name" id="namelist">
                    <div className="orderstitle" id="orders_userstitle">
                        <h2>Username</h2>
                    </div>
                    {
                        this.props.ele.map(item => (
                            <button id={"orders_usersbtn" + item} onClick={(e) => this.props.parent.selectuser(e,item)} className="orderselement">
                                <p>{item}</p>
                            </button>
                        ))
                    }
                </div>
            )
        }
        else if (this.props.options==="orders_orders")
        {
            return(
                <div className="name" id="namelist">
                    <div className="orderstitle" id="orders_orderstitle">
                        <h2>Orderid</h2>
                    </div>
                    {
                        this.props.ele.map(item => (
                            <button id={"orders_ordersbtn" + item} onClick={(e) => this.props.parent.selectorder(e,item)} className="orderselement">
                                <p>{item}</p>
                            </button>
                        ))
                    }
                </div>
            )
        }
        else if (this.props.options==="orders_books")
        {
            return(
                <div className="name" id="namelist">
                    <div className="orderstitle" id="orders_bookstitle">
                        <h2>Bookname</h2>
                    </div>
                    {
                        this.props.ele.map(item => (
                            <button id={"orders_booksbtn" + item} className="orderselement">
                                <p>{item}</p>
                            </button>
                        ))
                    }
                </div>
            )
        }
        else if (this.props.options==="statistics_sale")
        {
            return(
                <div className="name" id="namelist">
                    <div className="statisticstitle" id="statistics_saletitle">
                        <h2>Sale rank</h2>
                    </div>
                    {
                        this.props.ele.map(item => (
                            <button id={"statistics_salebtn" + item} className="statisticselement">
                                <p>{item[0]+"(totnum:"+item[1]+")"}</p>
                            </button>
                        ))
                    }
                </div>
            )
        }
        else if (this.props.options==="statistics_user")
        {
            return(
                <div className="name" id="namelist">
                    <div className="statisticstitle" id="statistics_usertitle">
                        <h2>User rank</h2>
                    </div>
                    {
                        this.props.ele.map(item => (
                            <button id={"statistics_userbtn" + item} className="statisticselement">
                                <p>{item[0]+"(totnum:"+item[1]+")"}</p>
                            </button>
                        ))
                    }
                </div>
            )
        }
        else if (this.props.options==="userstatistics")
        {
            return(
                <div className="name" id="namelist">
                    <div className="statisticstitle" id="userstatistics">
                        <h2>Categoryname</h2><h2>Buynum</h2>
                    </div>
                    {
                        this.props.ele.map(item => (
                            <button id={"userstatistics_btn" + item} className="statisticselement">
                                <p>{item[0]}</p> <p>{item[1]}</p>
                            </button>
                        ))
                    }
                </div>
            )
        }
    }
}