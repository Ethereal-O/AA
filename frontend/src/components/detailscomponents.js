import React from 'react';
import '../css/allbooks.css';
import '../css/mybooks.css';
import '../css/mycarts.css';
import { adminchangedata } from './connect_to_backend';

export class Details extends React.Component{


    constructor(props){
        super(props);
        this.state={
            detaildata:this.props.nowselecteddata,
            isedit:[false,false,false,false,false,false,false],
            source:this.props.nowselecteddata[5],
            // source:require(`../pics/${this.props.nowselecteddata[5]}.jpg`)
        }
    }

    componentWillReceiveProps(nextProps){
        this.setState({
            detaildata:nextProps.nowselecteddata,
            isedit:[false,false,false,false,false,false,false],
            source:nextProps.nowselecteddata[5],
            // source:require(`../pics/${nextProps.nowselecteddata[5]}.jpg`)
        })
    }

    edit(options){
        if (localStorage.getItem("isloged")!=="true"||localStorage.getItem("userstate")!=="2") return;
        let neweditstate=[false,false,false,false,false,false,false];
        neweditstate[options-2]=true;
        this.setState({
            isedit:neweditstate
        })
    }

    submit(e,options){
        let adminchangedatacallback=(susflag)=>
        {
            if(susflag)
            {
                alert("Change data Success!");
                this.props.parent.updatealldata();
            }else{
                alert("Change data false!");
            }
        }
        if (e.keyCode===13){
            let index = this.props.nowselecteddata[0];
            if (index===-1){
                this.setState({
                    isedit:[false,false,false,false,false,false,false]
                });
                return;
            }
            let content = e.currentTarget.value;
            adminchangedata(index,options, content,adminchangedatacallback);
        }
    }

    render(){
        let buttonreturn;
        if (this.props.options === "allbooks") {
            if(localStorage.getItem("isloged")==="true"&&localStorage.getItem("userstate")==="2")
            {
                buttonreturn=<div><button className="buybutton" onClick={(e) => this.props.parent.deletebook(e,this.props.nowselecteddata[0])}>Delete book</button><button className="buybutton" onClick={(e) => this.props.parent.addbook(e,this.props.nowselecteddata[1])}>Add book</button></div>
            }else{
                buttonreturn=<button className="buybutton" onClick={(e) => this.props.parent.buyclick(e)}>Add it to my cart</button>
            }
            } else {
        if (this.props.options === "mybooks"){
            buttonreturn=<button onClick={(e) => this.props.parent.readit(e)}>Read it</button>
        }else{
            buttonreturn=<button onClick={(e) => this.props.parent.deleteit(e)}>Delete it</button>
        }
        }
        return(
            <div className="details" id="bookdetails">
                {buttonreturn}
                <div className='namedetails' id='namedetails'>
                <h2>Name:</h2>
                {
                    this.state.isedit[0]?
                    <input defaultValue={this.props.nowselecteddata[2]} onKeyDown={(e)=>this.submit(e,2)}></input>:<h2 onClick={()=>this.edit(2)}>{this.props.nowselecteddata[2]}</h2>
                }
                </div>
                <div className='authordetails' id='authordetails'>
                <p>Author:</p>
                {
                    this.state.isedit[1]?
                    <input defaultValue={this.props.nowselecteddata[3]} onKeyDown={(e)=>this.submit(e,3)}></input>:<p onClick={()=>this.edit(3)}>{this.props.nowselecteddata[3]}</p>
                }
                </div>
                <div className='pricedetails' id='pricedetails'>
                <p>Price:</p>
                {
                    this.state.isedit[2]?
                    <input defaultValue={this.props.nowselecteddata[4]} onKeyDown={(e)=>this.submit(e,4)}></input>:<p onClick={()=>this.edit(4)}>{parseFloat(this.props.nowselecteddata[4]).toFixed(2)}</p>
                    
                }
                <p>dollars</p>
                </div>
                <div className='iddetails' id='iddetails'>
                <p>Id:</p>
                <p>{this.props.nowselecteddata[0]}</p>
                </div>
                <div className='inventorydetails' id='inventorydetails'>
                <p>Inventory:</p>
                {
                    this.state.isedit[5]?
                    <input defaultValue={this.props.nowselecteddata[7]} onKeyDown={(e)=>this.submit(e,7)}></input>:<p onClick={()=>this.edit(7)}>{this.props.nowselecteddata[7]}</p>
                }
                </div>
                <div className='ISBNdetails' id='ISBNdetails'>
                <p>ISBN:</p>
                {
                    this.state.isedit[6]?
                    <input defaultValue={this.props.nowselecteddata[8]} onKeyDown={(e)=>this.submit(e,8)}></input>:<p onClick={()=>this.edit(8)}>{this.props.nowselecteddata[8]}</p>
                }
                </div>
                {
                    this.state.isedit[3]?
                    <input defaultValue={this.props.nowselecteddata[5]} onKeyDown={(e)=>this.submit(e,5)}></input>:<img onClick={()=>this.edit(5)} src={this.state.source}></img>
                }
                <div className='descriptiondetails' id='descriptiondetails'>
                <p>Description:</p>
                {
                    this.state.isedit[4]?
                    <input defaultValue={this.props.nowselecteddata[6]} onKeyDown={(e)=>this.submit(e,6)}></input>:<p onClick={()=>this.edit(6)}>{this.props.nowselecteddata[6]}</p>
                }
                </div>
                
            </div>
        )
    }
}