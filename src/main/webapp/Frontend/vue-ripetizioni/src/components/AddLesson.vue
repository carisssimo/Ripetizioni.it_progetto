<template>
  <!--titolo e descrizione-->
  <div class="title-container container">
    <h1 class="title-ripetizioni">Ripetizioni.it</h1>
    <p class="p-start">Inserimento nuova lezione</p>
  </div>

  <!--FORM-->
  <div class="form-container container align-items-center">
    <form v-on:submit.prevent="onSubmit">
      <div class=" form-group">
        <label for="name">ID_Docente</label>
        <input type="text" class="form-group-orange form-control" id="id_prof" v-model="id_prof">
      </div>
      <div class="form-group">
        <label for="surname">ID_Corso</label>
        <input type="text" class="form-group-orange form-control" id="id_sub" v-model="id_sub">
      </div>
      <div class="form-group">
        <label for="surname">Giorno e orario</label>
        <input type="text" class="form-group-orange form-control" id="daytime" v-model="daytime">
      </div>




      <button class="btn-login btn btn-primary" @click="add">Aggiungi </button>
    </form>
  </div>


</template>

<script>
import axios from "axios";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "AddLesson",
  data: function(){
    return{
      id_prof:'',
      id_sub:'',
      daytime:''
    }
  },
  methods:{
    add(){
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'addLesson',
        id_prof:this.id_prof,
        id_sub:this.id_sub,
        daytime:this.daytime,

      };
      axios.get(url, {params}) /*prima effettuiamo la http request async*/
          .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
            if (response.data === "Added") {
              console.log("lezione aggiunta con successo")
              this.id_prof='';
              this.id_sub='';
              this.daytime='';
            } else {
              alert("problema aggiunta lezione");
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