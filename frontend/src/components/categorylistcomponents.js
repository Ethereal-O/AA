import React from "react";
import '../css/mybooks.css'
import '../css/allbooks.css'

export class CategoryList extends React.Component{
    render(){
        return(
            <div className="category" id="categorylist">
                <div className="categoryelement" id="categorytitle">
                    <h2>Category</h2>
                </div>
                <button id="btncateall" className="categoryelement" onClick={(e) => this.props.parent.selectcategory(e)}>
                    <p></p><p>全部</p>
                </button>
                <button id="btncatemasterpiece" className="categoryelement" onClick={(e) => this.props.parent.selectcategory(e)}>
                    <p>世界名著</p>
                </button>
                <button id="btncateteachingassistant" className="categoryelement" onClick={(e) => this.props.parent.selectcategory(e)}>
                    <p>中小学教辅</p>
                </button>
                <button id="btncatebiography" className="categoryelement" onClick={(e) => this.props.parent.selectcategory(e)}>
                    <p>传记文学</p>
                </button>
                <button id="btncatechildrenliterature" className="categoryelement" onClick={(e) => this.props.parent.selectcategory(e)}>
                    <p>儿童文学</p>
                </button>
                <button id="btncateancientbooks" className="categoryelement" onClick={(e) => this.props.parent.selectcategory(e)}>
                    <p>古籍</p>
                </button>
                <button id="btncatesuspensenovel" className="categoryelement" onClick={(e) => this.props.parent.selectcategory(e)}>
                    <p>悬疑/推理小说</p>
                </button>
                <button id="btncatekongfustory" className="categoryelement" onClick={(e) => this.props.parent.selectcategory(e)}>
                    <p>武侠小说</p>
                </button>
                <button id="btncatesocialnovel" className="categoryelement" onClick={(e) => this.props.parent.selectcategory(e)}>
                    <p>社会小说</p>
                </button>
                <button id="btncatesciencefiction" className="categoryelement" onClick={(e) => this.props.parent.selectcategory(e)}>
                    <p>科幻小说</p>
                </button>
                <button id="btncateprogram" className="categoryelement" onClick={(e) => this.props.parent.selectcategory(e)}>
                    <p>编程</p>
                </button>
                <button id="btncateyouthliterature" className="categoryelement" onClick={(e) => this.props.parent.selectcategory(e)}>
                    <p>青春文学</p>
                </button>
                <button id="btncatemagicnovels" className="categoryelement" onClick={(e) => this.props.parent.selectcategory(e)}>
                    <p>魔幻小说</p>
                </button>
            </div>
        )
    }
}