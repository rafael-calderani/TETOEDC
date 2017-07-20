var express = require('express'),
    app = express(),
    comunidades = require('./comunidades'),
    comunidades_equipes = require('./comunidades_equipes');
	
app.set('port', (process.env.PORT || 5000));

app.use(express.static(__dirname + '/public'));
app.use('/comunidades', comunidades);
app.use('/comunidades', comunidades_equipes);

//var server = app.listen(5000);
var titulo = "TETO EDC";

//console.log('Servidor TETO EDC Express iniciado na porta %s', server.address().port);
app.listen(app.get('port'), function() {
  console.log('Servidor TETO EDC Express iniciado na porta', app.get('port'));
});