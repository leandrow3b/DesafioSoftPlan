import React from 'react';
import PessoaList from './components/pessoaList/PessoaList';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-toastify/dist/ReactToastify.css';
import './App.css';
import Acoes from './components/acoes/Acoes';

class App extends React.Component {

    constructor(props) {
        super(props)
        this.state = { pessoas : [] }
    }

    componentDidMount() {
        fetch('http://localhost:8080/api/pessoa')
            .then(data => data.json())
            .then( json => {
                this.setState({pessoas : json});
            })
    }

    render() {
        return (
            <div className="Main">
                <Acoes/>
                <PessoaList pessoas={this.state.pessoas}/>
            </div>
        );
    }
}

export default App;
