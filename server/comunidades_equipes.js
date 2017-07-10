var express = require('express');
var router = express.Router();

// middleware that is specific to this router
/*router.use(function timeLog(req, res, next) {
  console.log('Time: ', Date.now());
  next();
});*/

router.get('/tribo/equipeEDC', function(req, res) {
  res.send(EQUIPE_TRIBO);
});
router.get('/spama/equipeEDC', function(req, res) {
  res.send(EQUIPE_SPAMA);
});
router.get('/vilanova/equipeEDC', function(req, res) {
  res.send(EQUIPE_VILANOVA);
});
router.get('/verdinhas/equipeEDC', function(req, res) {
  res.send(EQUIPE_VERDINHAS);
});
router.get('/beverly/equipeEDC', function(req, res) {
  res.send(EQUIPE_BEVERLY);
});
router.get('/grilo/equipeEDC', function(req, res) {
  res.send(EQUIPE_GRILO);
});
router.get('/rioclaro/equipeEDC', function(req, res) {
  res.send(EQUIPE_RIOCLARO);
});
router.get('/pedrabranca/equipeEDC', function(req, res) {
  res.send(EQUIPE_PEDRABRANCA);
});
router.get('/saofrancisco/equipeEDC', function(req, res) {
  res.send(EQUIPE_SAOFRANCISCO);
});
router.get('/souzaramos/equipeEDC', function(req, res) {
  res.send(EQUIPE_SOUZARAMOS);
});
router.get('/santamargarida/equipeEDC', function(req, res) {
  res.send(EQUIPE_SANTAMARGARIDA);
});
router.get('/olaria/equipeEDC', function(req, res) {
  res.send(EQUIPE_OLARIA);
});
router.get('/olga/equipeEDC', function(req, res) {
  res.send(EQUIPE_OLGA);
});
router.get('/vargemgrande/equipeEDC', function(req, res) {
  res.send(EQUIPE_VARGEMGRANDE);
});
router.get('/murao/equipeEDC', function(req, res) {
  res.send(EQUIPE_MURAO);
});
router.get('/portodeareia/equipeEDC', function(req, res) {
  res.send(EQUIPE_PORTODEAREIA);
});
router.get('/fazendinha/equipeEDC', function(req, res) {
  res.send(EQUIPE_FAZENDINHA);
});
router.get('/chatuba/equipeEDC', function(req, res) {
  res.send(EQUIPE_CHATUBA);
});
router.get('/malvinas/equipeEDC', function(req, res) {
  res.send(EQUIPE_MALVINAS);
});
router.get('/novaconquista/equipeEDC', function(req, res) {
  res.send(EQUIPE_NOVACONQUISTA);
});
router.get('/portelinha/equipeEDC', function(req, res) {
  res.send(EQUIPE_PORTELINHA);
});
router.get('/pinheirinho/equipeEDC', function(req, res) {
  res.send(EQUIPE_PINHEIRINHO);
});
router.get('/catapreta/equipeEDC', function(req, res) {
  res.send(EQUIPE_CATAPRETA);
});
router.get('/vilamoraes/equipeEDC', function(req, res) {
  res.send(EQUIPE_VILAMORAES);
});
router.get('/pintassilva/equipeEDC', function(req, res) {
  res.send(EQUIPE_PINTASSILVA);
});

const EQUIPE_TRIBO = []
const EQUIPE_SPAMA = []
const EQUIPE_VILANOVA = []
const EQUIPE_VERDINHAS = []
const EQUIPE_BEVERLY = []
const EQUIPE_GRILO = []
const EQUIPE_RIOCLARO = []
const EQUIPE_PEDRABRANCA = []
const EQUIPE_SAOFRANCISCO = []
const EQUIPE_SOUZARAMOS = []
const EQUIPE_SANTAMARGARIDA = []
const EQUIPE_OLGA = []
const EQUIPE_VARGEMGRANDE = []
const EQUIPE_MURAO = []
const EQUIPE_PORTODEAREIA = []
const EQUIPE_FAZENDINHA = []
const EQUIPE_CHATUBA = []
const EQUIPE_MALVINAS = []
const EQUIPE_NOVACONQUISTA = []
const EQUIPE_PORTELINHA = []
const EQUIPE_PINHEIRINHO = []
const EQUIPE_CATAPRETA = []
const EQUIPE_VILAMORAES = []
const EQUIPE_PINTASSILVA = []
const EQUIPE_OLARIA = [
    {
        "nome" : "Rafael dos Santos Calderani",
        "funcao" : "Coordenador",
        "email" : "email@gmail.com",
        "telefone" : "11 99999-9999",
        "foto" : "/images/user_default.jpg"
    },
    {
        "nome" : "Bruna Galvão",
        "funcao" : "Coordenador",
        "email" : "email@gmail.com",
        "telefone" : "11 99999-9999",
        "foto" : "/images/user_default.jpg"
    },
    {
        "nome" : "Sami Elia",
        "funcao" : "Voluntario",
        "email" : "email@gmail.com",
        "telefone" : "11 99999-9999",
        "foto" : "/images/user_default.jpg"
    },
    {
        "nome" : "Raphael Dib",
        "funcao" : "Comunicação",
        "email" : "email@gmail.com",
        "telefone" : "11 99999-9999",
        "foto" : "/images/user_default.jpg"
    },
    {
        "nome" : "Mariana Vilela",
        "funcao" : "Jurídico",
        "email" : "email@gmail.com",
        "telefone" : "11 99999-9999",
        "foto" : "/images/user_default.jpg"
    },
    {
        "nome" : "Alice Teizen",
        "funcao" : "Jurídico",
        "email" : "email@gmail.com",
        "telefone" : "11 99999-9999",
        "foto" : "/images/user_default.jpg"
    },
    {
        "nome" : "William Otsubo",
        "funcao" : "Voluntario",
        "email" : "email@gmail.com",
        "telefone" : "11 99999-9999",
        "foto" : "/images/user_default.jpg"
    },
    {
        "nome" : "Lin Cheng Yu",
        "funcao" : "Voluntario",
        "email" : "email@gmail.com",
        "telefone" : "11 99999-9999",
        "foto" : "/images/user_default.jpg"
    },
    {
        "nome" : "Maytê Coelho",
        "funcao" : "Voluntario",
        "email" : "email@gmail.com",
        "telefone" : "11 99999-9999",
        "foto" : "/images/user_default.jpg"
    },
    {
        "nome" : "Livia Colabone",
        "funcao" : "Voluntario",
        "email" : "email@gmail.com",
        "telefone" : "11 99999-9999",
        "foto" : "/images/user_default.jpg"
    }
];

module.exports = router;