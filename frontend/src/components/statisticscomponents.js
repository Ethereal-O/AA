import React from 'react';
import '../css/statistics.css';
import { NameList } from './namelistcomponents';
import { Timeseacherbar } from './searchbarcomponents';

export class Statisticscontainer extends React.Component{

    data=[];
    constructor(props){
        super(props);
        let newbookdata=this.presetbookdata(props.data);
        let newuserdata=this.presetuserdata(props.data);
        this.state={
            alldata:props.data,
            nowdata:props.data,
            bookdata:newbookdata,
            userdata:newuserdata
        }
    }
    componentWillReceiveProps(nextProps){
        let newbookdata=this.presetbookdata(nextProps.data);
        let newuserdata=this.presetuserdata(nextProps.data);
        this.setState({
            alldata:nextProps.data,
            nowdata:nextProps.data,
            bookdata:newbookdata,
            userdata:newuserdata
        })
    }

    presetbookdata(data){
        let resultbookdata=[]
        let rankbookdata=[]
        for (let i=0;i<data.length;i++)
        {
            let existedindex=-1;
            for(let j=0;j<resultbookdata.length;j++)
            {
                if(resultbookdata[j][0]===data[i][2])
                {
                    existedindex=j;
                    break;
                    
                }
            }
            if (existedindex!=-1){
                resultbookdata[existedindex][1]++;
            }else{
                resultbookdata.push([data[i][2],1]);
            }
        }

        resultbookdata.sort(function(a,b){
            return a[1]>b[1]?-1:1;
        })

        return resultbookdata;
        
        for (let i = 0; i < resultbookdata.length; i++) {
            rankbookdata.push(resultbookdata[i][0]);
        }
        return rankbookdata;
    }

    presetuserdata(data){
        let resultuserdata=[]
        let rankuserdata=[]
        for (let i=0;i<data.length;i++)
        {
            let existedindex=-1;
            for(let j=0;j<resultuserdata.length;j++)
            {
                if(resultuserdata[j][0]===data[i][0])
                {
                    existedindex=j;
                    break;
                }
            }
            if (existedindex!=-1){
                resultuserdata[existedindex][1]++;
            }else{
                resultuserdata.push([data[i][0],1]);
            }
        }

        resultuserdata.sort(function(a,b){
            return a[1]>b[1]?-1:1;
        })

        return resultuserdata;
        
        for (let i = 0; i < resultuserdata.length; i++) {
            rankuserdata.push(resultuserdata[i][0]);
        }
        return rankuserdata;
    }

    updatealldata(){
        this.props.parent.updatealldata();
    }

    timesearchdata=(starttime,endtime)=>{
        var newdata=[];
        for (let i=0;i<this.state.alldata.length;i++){
            if(this.state.alldata[i][3]>=starttime&&this.state.alldata[i][3]<=endtime){
                newdata.push(this.state.alldata[i]);
            }
        }
        let newbookdata=this.presetbookdata(newdata);
        let newuserdata=this.presetuserdata(newdata);
        this.setState({
            nowdata:newdata,
            bookdata:newbookdata,
            userdata:newuserdata
        })

    }

    render(){
        return(
            <div className="statisticscontainer">
                <div className="card">
                    <NameList ele={this.state.bookdata} parent={this} options="statistics_sale" ></NameList>
                    <NameList ele={this.state.userdata} parent={this} options="statistics_user" ></NameList>
                </div>
                <Timeseacherbar parent={this}></Timeseacherbar>
            </div>
        )
    }
}
