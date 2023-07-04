<template>
  <!--titolo e descrizione-->
  <div class="title-container container">
    <h1 class="title-ripetizioni">Ripetizioni.it</h1>
    <p class="p-start">Eliminazione Materia</p>
  </div>

  <!--FORM-->
  <div v-if="!isSigned" class="form-container container align-items-center">
    <form v-on:submit.prevent="onSubmit">
      <div class=" form-group">
        <label for="name">Nome</label>
        <input type="text" class="form-group-orange form-control" id="name" v-model="name">

      </div>
      <div class="form-group">
        <label for="surname">Descrizione</label>
        <input type="text" class="form-group-orange form-control" id="surname" v-model="descp">
      </div>



      <button class="btn-login btn btn-primary" @click="add">Rimuovi </button>
    </form>
  </div>


</template>

<script>
import axios from "axios";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "RemoveSubject",
  data: function(){
    return{
      isSigned:false,
      name:'',
      descp:'',


    }
  },
  methods:{
    add(){
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'RemoveSubject',
        name:this.name,
        descp:this.descp,

      };
      axios.get(url, {params}) /*prima effettuiamo la http request async*/
          .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
            if (response.data === "Added") {
              console.log(" aggiunto con successo")
              this.name='';
              this.descp='';
            } else {
              alert("problema aggiunta materia");
            }

          })
          .catch(error => {

            console.error(error);
          });
    }
  }
}
</script>

<style scoped>
.logged-container {
  padding-top: 50px;
  padding-bottom: 50px;
  border: solid orangered 2px;
  border-radius: 10px;
  margin-top: 50px;
  background-color: white;
}
</style>