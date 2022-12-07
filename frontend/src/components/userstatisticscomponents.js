import React from 'react';
import '../css/userstatistics.css';
import { NameList } from './namelistcomponents';
import { Timeseacherbar } from './searchbarcomponents';
import { CategoryList } from './categorylistcomponents';

export class UserStatisticscontainer extends React.Component{

    data=[];
    constructor(props){
        super(props);
        let newcatenumdata=this.presetdata(props.data);
        this.state={
            alldata:props.data,
            nowdata:props.data,
            nowcatenumdata:newcatenumdata,
        }
    }
    componentWillReceiveProps(nextProps){
        let newcatenumdata=this.presetdata(nextProps.data);
        this.setState({
            alldata:nextProps.data,
            nowdata:nextProps.data,
            nowcatenumdata:newcatenumdata,
        })
    }

    presetdata(data){
        let categorylist=["全部","世界名著","中小学教辅","传记文学","儿童文学","古籍","悬疑/推理小说","武侠小说","社会小说","科幻小说","编程","青春文学","魔幻小说"];
        let categorynumlist=[];
        for (let j = 0; j < categorylist.length; j++) {
            categorynumlist.push(0);
        }
        for(let i=0;i<data.length;i++)
        {
            for (let j=0;j<categorylist.length;j++)
            {
                if(categorylist[j]===data[i][1])
                {
                    categorynumlist[j]++;
                }
            }
            categorynumlist[0]++;
        }
        let resultnumlist=[];
        for (let j=0;j<categorylist.length;j++)
        {
            resultnumlist.push([categorylist[j],categorynumlist[j]]);
        }
        return resultnumlist;
    }


    updatealldata(){
        this.props.parent.updatealldata();
    }

    timesearchdata=(starttime,endtime)=>{
        var newdata=[];
        for (let i=0;i<this.state.alldata.length;i++){
            if(this.state.alldata[i][9]>=starttime&&this.state.alldata[i][9]<=endtime){
                newdata.push(this.state.alldata[i]);
            }
        }
        let newcatenumdata=this.presetdata(newdata);
        this.setState({
            nowdata:newdata,
            nowcatenumdata:newcatenumdata,
        })

    }
    selectcategory=(e)=>{

    }

    render(){
        let totprice=0;
        for(let j=0;j<this.state.nowdata.length;j++)
        {
            totprice+=parseFloat(this.state.nowdata[j][4]);
        }
        return(
            <div className="userstatisticscontainer">
                <div className="card">
                    <NameList ele={this.state.nowcatenumdata} parent={this} options="userstatistics" ></NameList>
                </div>
                <div className="statisticsbar">Totprice:{totprice.toFixed(2)} dollars  Totnum:{this.state.nowcatenumdata[0][1]}</div>
                <Timeseacherbar parent={this}></Timeseacherbar>
            </div>
        )
    }
}
