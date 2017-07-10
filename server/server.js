var express = require('express'),
    app = express(),
    comunidades = require('./comunidades'),
    comunidades_equipes = require('./comunidades_equipes'),

app.use(express.static('public'));

app.use('/comunidades', comunidades);
app.use('/comunidades', comunidades_equipes);

var server = app.listen(3000);
var titulo = "TETO EDC";

console.log('Servidor %s Express iniciado na porta %s', titulo, server.address().port);