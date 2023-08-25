
module.exports = {
  devServer: {
    port: 8081, // Cambia il numero di porta a quello desiderato
    /*proxy: 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet'*/ // Cambia con l'URL del tuo server backend
  },
  transpileDependencies: true
};