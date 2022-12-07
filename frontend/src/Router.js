import React from 'react';
import { Router, Route, Switch, Redirect} from 'react-router-dom';
import {history} from "./utils/history";
import Home from "./view/home";
import Allbooks from "./view/allbooks";
import Mybooks from "./view/mybooks";
import Mycarts from './view/mycarts';
import Allusers from './view/allusers';
import Allorders from './view/allorders';
import Statistics from './view/statistics';
import UserStatistics from './view/userstatistics';

class BasicRoute extends React.Component{

    constructor(props) {
        super(props);

        history.listen((location, action) => {
            // clear alert on location change
            console.log(location,action);
        });
    }

    render(){
        return(
            <Router history={history}>
                <Switch>
                    <Route exact path="/" component={Home} />
                    <Route exact path="/Allbooks" component={Allbooks} />
                    <Route exact path="/Mybooks" component={Mybooks} />
                    <Route exact path="/Mycarts" component={Mycarts} />
                    <Route exact path="/Allusers" component={Allusers} />
                    <Route exact path="/Allorders" component={Allorders} />
                    <Route exact path="/Statistics" component={Statistics} />
                    <Route exact path="/UserStatistics" component={UserStatistics} />
                    <Redirect from="/*" to="/" />
                </Switch>

            </Router>
        )
    }


}

export default BasicRoute;