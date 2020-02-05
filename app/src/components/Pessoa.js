import React from 'react';

class Pessoa extends React.Component {
    render() {
        const pessoa = this.props.pessoa;
        return (
            <tr onClick={this.props.customClick}>
                <td>{pessoa.id}</td>
                <td>{pessoa.nome}</td>
                <td>{pessoa.cpf}</td>
                <td>{pessoa.sexo}</td>
                <td>{pessoa.email}</td>
                <td>{pessoa.dtNascimento}</td>
                <td>{pessoa.naturalidade}</td>
                <td>{pessoa.nacionalidade}</td>
            </tr>
        );
    }
}

export default Pessoa;