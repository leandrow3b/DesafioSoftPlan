import React from 'react';
import Pessoa from '../Pessoa';
import { Table } from 'react-bootstrap';
import { ButtonToolbar } from 'react-bootstrap';
import { Button } from 'react-bootstrap';
import { Modal } from 'react-bootstrap';
import { Form } from 'react-bootstrap';
import { Col } from 'react-bootstrap';
import './PessoaList.css';
import { ToastContainer, toast } from 'react-toastify';
import  { mascaraCpf, mascaraData } from './../util/Utilidades';
import moment from 'moment';

class PessoaList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            mostrar : false,
            pessoaAlterar: {},
            nome: "",
            cpf: "",
            email: "",
            sexo: "",
            dtNascimento: "",
            nacionalidade: "",
            naturalidade: ""
        }
    };

    _handleChange = (campo, e) => {
        if(campo === 'cpf') {
            this.setState({[campo]: mascaraCpf(e.target.value)});
        } else if (campo === 'dtNascimento') {
            this.setState({[campo]: mascaraData(e.target.value)});
        } else {
            this.setState({[campo]: e.target.value});
        }
    };

    _handleFechar = () => {
        this.setState({mostrar : false});
    };
    _handleMostrar = (pessoa, e) => {
        this.setState({
            pessoaAlterar: pessoa,
            nome: pessoa.nome,
            cpf: pessoa.cpf,
            email: pessoa.email,
            sexo: pessoa.sexo,
            dtNascimento: moment(pessoa.dtNascimento, "YYYY-MM-DD").format("DD/MM/YYYY"),
            nacionalidade: pessoa.nacionalidade,
            naturalidade: pessoa.naturalidade
        })
        this.setState({mostrar : true});
    };

    _handleAlterar = () => {
        fetch(`http://localhost:8080/api/pessoa/${this.state.pessoaAlterar.id}` , 
            {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    nome: this.state.nome,
                    email: this.state.email,
                    cpf: this.state.cpf,
                    sexo: this.state.sexo,
                    dtNascimento: moment(this.state.dtNascimento, 'DD/MM/YYYY').format('YYYY-MM-DD'),
                    nacionalidade: this.state.nacionalidade,
                    naturalidade: this.state.naturalidade
                })
            }).then((response) => {
                if(response.status === 200) {
                    toast.success('✔ Pessoa alterada com sucesso! ✖', {
                        position: toast.POSITION.TOP_RIGHT,
                        autoClose: 1500,
                        onClose : () => {
                            this._handleFechar();
                            window.location.reload();
                        }
                    });
                } else if (response.status === 406) {
                    toast.error('✖ CPF já cadastrado!', {
                        position: toast.POSITION.TOP_RIGHT
                    });
                } else {
                    let body = response.json();
                    let erro = body.errors[0];
                    if(erro) {
                        let mensagem = erro.defaultMessage;
                        toast.error(`✖ ${mensagem}`, {
                            position: toast.POSITION.TOP_RIGHT,
                            autoClose: 1500,
                        });
                    } else {
                        toast.error('✖ Erro ao adicionar pessoa!', {
                            position: toast.POSITION.TOP_RIGHT
                        });
                    }
                }
            });
    }

    _handleDeletar = () => {
        fetch(`http://localhost:8080/api/pessoa/${this.state.pessoaAlterar.id}` , 
            {
                method: 'DELETE'
            }).then((data) => {
                if(data.status === 200) {
                    toast.success('✔ Pessoa deletada com sucesso! ✖', {
                        position: toast.POSITION.TOP_RIGHT,
                        autoClose: 1500,
                        onClose : () => {
                            this._handleFechar();
                            window.location.reload();
                        }
                    });
                } else {
                    let erro = data.errors[0];
                    if(erro) {
                        let mensagem = erro.defaultMessage;
                        toast.error(`✖ ${mensagem}`, {
                            position: toast.POSITION.TOP_RIGHT,
                            autoClose: 1500,
                        });
                    } else {
                        toast.error('✖ Erro ao alterar pessoa!', {
                            position: toast.POSITION.TOP_RIGHT
                        });
                    }
                }
            });
    }

    render() {
        const pessoas = this.props.pessoas.map(pessoa => 
            <Pessoa key={pessoa.id} pessoa={pessoa} customClick={(e) => this._handleMostrar(pessoa, e)}/>
        )
        return(
            <div>
                <Table>
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Nome</th>
                            <th>Cpf</th>
                            <th>Sexo</th>
                            <th>Email</th>
                            <th>Nascimento</th>
                            <th>Naturalidade</th>
                            <th>Nacionalidade</th>
                        </tr>
                    </thead>
                    <tbody>
                        {pessoas}
                    </tbody>
                </Table>

                <Modal 
                    show={this.state.mostrar} 
                    onHide={this._handleFechar}
                    aria-labelledby="contained-modal-title-vcenter"
                    size="lg" centered>
                    <Modal.Header>
                        <Modal.Title>Adicionar Pessoa</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form>
                            <Form.Row>
                                <Form.Group as={Col}>
                                    <Form.Label>Nome</Form.Label>
                                    <Form.Control type="text" placeholder="Preencha o Nome" 
                                        value={this.state.nome} 
                                        onChange={(e) => this._handleChange("nome", e)}/>
                                </Form.Group>
                                <Form.Group as={Col}>
                                    <Form.Label>Cpf</Form.Label>
                                    <Form.Control type="text" placeholder="Preencha o CPF" 
                                        value={this.state.cpf} 
                                        onChange={(e) => this._handleChange("cpf", e)}/>
                                </Form.Group>
                            </Form.Row>
                            <Form.Row>
                                <Form.Group as={Col}>
                                    <Form.Label>Email</Form.Label>
                                    <Form.Control type="email" placeholder="Preencha o Email"
                                    value={this.state.email}
                                    onChange={(e) => this._handleChange("email", e)}/>
                                </Form.Group>
                            </Form.Row>
                            <Form.Row>
                                <Form.Group as={Col}>
                                    <Form.Label>Sexo</Form.Label>
                                    <Form.Control as="select" 
                                        value={this.state.sexo} 
                                        onChange={(e) => this._handleChange("sexo", e)}>
                                        <option>---</option>
                                        <option>Masculino</option>
                                        <option>Feminino</option>
                                    </Form.Control>
                                </Form.Group>
                                <Form.Group as={Col}>
                                    <Form.Label>Data Nascimento</Form.Label>
                                    <Form.Control type="text" placeholder="Preencha a data de nascimento"
                                    value={this.state.dtNascimento}
                                    onChange={(e) => this._handleChange("dtNascimento", e)}/>
                                </Form.Group>
                            </Form.Row>
                            <Form.Row>
                                <Form.Group as={Col}>
                                    <Form.Label>Naturalidade</Form.Label>
                                    <Form.Control type="text" placeholder="Preencha a naturalidade"
                                    value={this.state.naturalidade}
                                    onChange={(e) => this._handleChange("naturalidade", e)}/>
                                </Form.Group>
                                <Form.Group as={Col}>
                                    <Form.Label>Nacionalidade</Form.Label>
                                    <Form.Control type="text" placeholder="Preencha a nacionalidade"
                                    value={this.state.nacionalidade}
                                    onChange={(e) => this._handleChange("nacionalidade", e)}/>
                                </Form.Group>
                            </Form.Row>
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <ButtonToolbar>
                            <Button variant="warning" onClick={this._handleAlterar} className="espaco">Alterar</Button>
                            <Button variant="danger" onClick={this._handleDeletar} >Deletar</Button>
                        </ButtonToolbar>
                    </Modal.Footer>
                </Modal>

                <ToastContainer />
            </div>
        );
    }
}

export default PessoaList;