import React from "react";
import '../css/mybooks.css'
import '../css/allbooks.css'

export class Searchbar extends React.Component{

    render(){
        return(
            <div className="searchbar">
                <form>
                    <input className="searchdata" id="searchdata" type="text" name="searchdata"></input>
                    <input className="searchbutton" type="button" value="Search" onClick={() => this.props.parent.searchdata()} />
                </form>
            </div>
        )
    }
}

export class Timeseacherbar extends React.Component{

    constructor(props)
    {
        super(props);
        this.state={
            starttime:0,
            endtime:0,
        }
            
    }

    changestarttime=(e)=>{
        let newstarttime=e.target.value;
        this.setState({
            starttime:newstarttime
        })
    }

    changeendtime=(e)=>{
        let newendtime=e.target.value;
        this.setState({
            endtime:newendtime
        })
    }

    render(){
        return (
            <div className="searchbar">
                <p>TimeSearch:(Format:YYYY-MM-DD HH:MM:SS)</p>
                <form>
                    <input className="timesearchdata" id="timesearchstart" type="text" name="timesearchstart" onChange={(e)=>this.changestarttime(e)}></input>
                    <input className="timesearchdata" id="timesearchend" type="text" name="timesearchend" onChange={(e)=>this.changeendtime(e)}></input>
                    <input className="searchbutton" type="button" value="Search" onClick={() => this.props.parent.timesearchdata(this.state.starttime,this.state.endtime)} />
                </form>
            </div>
        )
    }
}