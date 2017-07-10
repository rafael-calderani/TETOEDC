var express = require('express');
var router = express.Router();

router.get('/', function(req, res) {
  res.send(COMUNIDADES);
});

const COMUNIDADES = [
    {
        "id" : "1",
        "nome" : "Tribo",
        "regiao" : "Zona Norte",
        "uf" : "SP",
        "cidade" : "São Paulo",
		"bairro" : "",
        "enderecoEntrada" : "Rua Gregorio Pomar, 384",
        "dataEntrada" : "",
        "descricao" : "",
        "latitude" : "-23.448623",
        "longitude" : "-46.695958"
    },
    {
        "id" : "2",
        "nome" : "Spama",
        "regiao" : "Zona Norte",
        "uf" : "SP",
        "cidade" : "São Paulo",
		"bairro" : "Jardim Iris",
        "enderecoEntrada" : "Avenida Raimundo Pereira de Magalhães, 3290",
        "dataEntrada" : "01/06/2014",
        "descricao" : "",
        "latitude" : "-23.497253",
        "longitude" : "-46.727094"
    },
    {
        "id" : "3",
        "nome" : "Vila Nova Esperança",
        "regiao" : "Zona Oeste",
        "uf" : "SP",
        "cidade" : "São Paulo",
		"bairro" : "Butantã",
        "enderecoEntrada" : "Av Engenheiro Heitor Eiras Garcia, 9450",
        "dataEntrada" : "01/01/2011",
        "descricao" : "",
        "latitude" : "-23.576192",
        "longitude" : "-46.742087"
    },
    {
        "id" : "4",
        "nome" : "Verdinhas",
        "regiao" : "Zona Leste",
        "uf" : "SP",
        "cidade" : "São Paulo",
		"bairro" : "Limoeiro",
        "enderecoEntrada" : "Rua Padre Clemente Segura X Rua João Batista Calógeras (Bairro Limoeiro)",
        "dataEntrada" : "01/01/1982",
        "descricao" : "",
        "latitude" : "-23.516005",
        "longitude" : "-46.470587"
    },
    {
        "id" : "5",
        "nome" : "Beverly Hills",
        "regiao" : "Zona Leste",
        "uf" : "SP",
        "cidade" : "São Paulo",
		"bairro" : "Itaim Paulista",
        "enderecoEntrada" : "Rua Manoel Rodrigues Santiago, 100",
        "dataEntrada" : "01/06/2014",
        "descricao" : "",
        "latitude" : "-23.511646",
        "longitude" : "-46.378949"
    },
    {
        "id" : "6",
        "nome" : "Grilo",
        "regiao" : "Zona Leste",
        "uf" : "SP",
        "cidade" : "São Paulo",
		"bairro" : "",
        "enderecoEntrada" : "",
        "dataEntrada" : "",
        "descricao" : "",
        "latitude" : "",
        "longitude" : ""
    },
    {
        "id" : "7",
        "nome" : "Jardim Rio Claro",
        "regiao" : "Zona Leste",
        "uf" : "SP",
        "cidade" : "São Paulo",
		"bairro" : "",
        "enderecoEntrada" : "",
        "dataEntrada" : "",
        "descricao" : "",
        "latitude" : "",
        "longitude" : ""
    },
    {
        "id" : "8",
        "nome" : "Pedra Branca",
        "regiao" : "Zona Leste",
        "uf" : "SP",
        "cidade" : "São Paulo",
		"bairro" : "Jardim Pedra Branca",
        "enderecoEntrada" : "Estrada do Iguatemi, 1088",
        "dataEntrada" : "01/10/2015",
        "descricao" : "",
        "latitude" : "-23.570429",
        "longitude" : "-46.415671"
    },
    {
        "id" : "9",
        "nome" : "São Francisco",
        "regiao" : "Zona Leste",
        "uf" : "SP",
        "cidade" : "São Paulo",
		"bairro" : "",
        "enderecoEntrada" : "",
        "dataEntrada" : "",
        "descricao" : "",
        "latitude" : "",
        "longitude" : ""
    },
    {
        "id" : "10",
        "nome" : "Souza Ramos",
        "regiao" : "Zona Leste",
        "uf" : "SP",
        "cidade" : "São Paulo",
		"bairro" : "",
        "enderecoEntrada" : "Avenida Souza Ramos x Rua Inácio Monteiro",
        "dataEntrada" : "01/06/2002",
        "descricao" : "",
        "latitude" : "-23.564340",
        "longitude" : "-46.413365"
    },
    {
        "id" : "11",
        "nome" : "Vila Santa Margarida",
        "regiao" : "Zona Leste",
        "uf" : "SP",
        "cidade" : "São Paulo",
		"bairro" : "Ferraz de Vasconcelos",
        "enderecoEntrada" : "Rua São João x Av. Hermenegildo Barreto",
        "dataEntrada" : "01/06/2014",
        "descricao" : "",
        "latitude" : "-23.517187",
        "longitude" : "-46.377213"
    },
    {
        "id" : "12",
        "nome" : "Olaria",
        "regiao" : "Zona Sul",
        "uf" : "SP",
        "cidade" : "São Paulo",
		"bairro" : "Jardim Iris",
        "enderecoEntrada" : "Rua Francisco de Sales, 200",
        "dataEntrada" : "01/01/1992",
        "descricao" : "",
        "latitude" : "-23.622455",
        "longitude" : "-46.747204"
    },
    {
        "id" : "13",
        "nome" : "Olga Benário",
        "regiao" : "Zona Sul",
        "uf" : "SP",
        "cidade" : "São Paulo",
		"bairro" : "",
        "enderecoEntrada" : "",
        "dataEntrada" : "",
        "descricao" : "",
        "latitude" : "",
        "longitude" : ""
    },
    {
        "id" : "14",
        "nome" : "Vargem Grande",
        "regiao" : "Zona Sul",
        "uf" : "SP",
        "cidade" : "São Paulo",
		"bairro" : "",
        "enderecoEntrada" : "Rua Juritis, Colônia",
        "dataEntrada" : "01/10/2016",
        "descricao" : "",
        "latitude" : "-23.862863",
        "longitude" : "-46.710422"
    },
    {
        "id" : "15",
        "nome" : "Murão",
        "regiao" : "Subregião Oeste",
        "uf" : "SP",
        "cidade" : "São Paulo",
		"bairro" : "Carapicuíba",
        "enderecoEntrada" : "Estrada do Pequiá, 100",
        "dataEntrada" : "01/08/2013",
        "descricao" : "",
        "latitude" : "-23.541416",
        "longitude" : "-46.835898"
    },
    {
        "id" : "16",
        "nome" : "Porto de Areia",
        "regiao" : "Subregião Oeste",
        "uf" : "SP",
        "cidade" : "Carapicuiba",
		"bairro" : "Vila Gustavo Correia",
        "enderecoEntrada" : "Av. Francisco Pignatari, 630",
        "dataEntrada" : "20/03/2016",
        "descricao" : "",
        "latitude" : "-23.519095",
        "longitude" : "-46.832638"
    },
    {
        "id" : "17",
        "nome" : "Fazendinha",
        "regiao" : "Subregião Oeste",
        "uf" : "SP",
        "cidade" : "Osasco",
		"bairro" : "Jardim Roberto",
        "enderecoEntrada" : "R. Nova Conceição, 369",
        "dataEntrada" : "01/06/2005",
        "descricao" : "",
        "latitude" : "-23.548075",
        "longitude" : "-46.812228"
    },
    {
        "id" : "18",
        "nome" : "Chatuba",
        "regiao" : "Subregião Leste",
        "uf" : "SP",
        "cidade" : "Guarulhos",
		"bairro" : "Guarulhos",
        "enderecoEntrada" : "Av. Salgado Filho, 3907",
        "dataEntrada" : "01/06/2012",
        "descricao" : "",
        "latitude" : "-23.435932",
        "longitude" : "-46.536316"
    },
    {
        "id" : "19",
        "nome" : "Malvinas",
        "regiao" : "Subregião Leste",
        "uf" : "SP",
        "cidade" : "Guarulhos",
		"bairro" : "Guarulhos",
        "enderecoEntrada" : "Rua dos Eucaliptos, 100",
        "dataEntrada" : "01/06/1982",
        "descricao" : "",
        "latitude" : "-23.414924",
        "longitude" : "-46.478537"
    },
    {
        "id" : "20",
        "nome" : "Nova Conquista",
        "regiao" : "Subregião Leste",
        "uf" : "SP",
        "cidade" : "Guarulhos",
		"bairro" : "",
        "enderecoEntrada" : "",
        "dataEntrada" : "01/06/2009",
        "descricao" : "",
        "latitude" : "",
        "longitude" : ""
    },
    {
        "id" : "21",
        "nome" : "Portelinha",
        "regiao" : "Subregião Leste",
        "uf" : "SP",
        "cidade" : "Guarulhos",
		"bairro" : "",
        "enderecoEntrada" : "Estrada Mato das Cobras, 300",
        "dataEntrada" : "01/01/2009",
        "descricao" : "",
        "latitude" : "-23.410224",
        "longitude" : "-46.412363"
    },
    {
        "id" : "22",
        "nome" : "Pinheirinho",
        "regiao" : "Subregião Sudoeste",
        "uf" : "SP",
        "cidade" : "Embu das Artes",
		"bairro" : "",
        "enderecoEntrada" : "",
        "dataEntrada" : "",
        "descricao" : "",
        "latitude" : "",
        "longitude" : ""
    },
    {
        "id" : "23",
        "nome" : "Cata Preta",
        "regiao" : "Subregião Sudoeste",
        "uf" : "SP",
        "cidade" : "São Bernardo do Campo",
		"bairro" : "Santo André",
        "enderecoEntrada" : "Estrada do Cata Preta, 1093",
        "dataEntrada" : "",
        "descricao" : "",
        "latitude" : "-23.717380",
        "longitude" : "-46.510772"
    },
    {
        "id" : "24",
        "nome" : "Vila Moraes",
        "regiao" : "Subregião Sudoeste",
        "uf" : "SP",
        "cidade" : "São Bernardo do Campo",
		"bairro" : "São Bernardo do Campo",
        "enderecoEntrada" : "Rua Cipriano Lopes França, 10.019",
        "dataEntrada" : "01/06/1954",
        "descricao" : "",
        "latitude" : "-23.609809",
        "longitude" : "-46.689742"
    },
    {
        "id" : "25",
        "nome" : "Pintassilva",
        "regiao" : "Subregião Sudoeste",
        "uf" : "SP",
        "cidade" : "Santo André",
		"bairro" : "Parque Miami",
        "enderecoEntrada" : "Rua Pintassilva - CEP 09133115",
        "dataEntrada" : "",
        "descricao" : "",
        "latitude" : "-23.737390",
        "longitude" : "-46.491941"
    },
]


module.exports = router;