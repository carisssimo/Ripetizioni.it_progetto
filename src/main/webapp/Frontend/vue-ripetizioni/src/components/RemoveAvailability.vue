<template>
  <!--titolo e descrizione-->
  <div class="title-container container">
    <h1 class="title-ripetizioni">Ripetizioni.it</h1>
    <p class="p-start">Eliminazione Disponibilità</p>
  </div>

  <!--FORM-->
  <div v-if="!isSigned" class="form-container container align-items-center">
    <form v-on:submit.prevent="onSubmit">
      <div class=" form-group">
        <label for="name">ID_DOCENTE</label>
        <input type="text" class="form-group-orange form-control" id="name" v-model="id_prof">

      </div>
      <div class="form-group">
        <label for="surname">ID_CORSO</label>
        <input type="text" class="form-group-orange form-control" id="surname" v-model="id_sub">
      </div>

      <div class="form-group">
        <label for="name">GIORNO_ORA</label>
        <input type="text" class="form-group-orange form-control" v-model="dateAv">
      </div>

      <button class="btn-login btn btn-primary" @click="add">Rimuovi </button>
    </form>
  </div>


</template>

<script>
import axios from "axios";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "RemoveAvailability",
  data: function(){
    return{

      id_prof:'',
      id_sub:'',
      dateAv:'',

    }
  },
  methods:{
    add(){
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'removeAvailability',
        id_prof:this.id_prof,
        id_sub:this.id_sub,
        dateAv: this.dateAv,
      };
      axios.get(url, {params}) /*prima effettuiamo la http request async*/
          .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
            if (response.data === "Removed") {
              console.log("disponibilità rimossa con successo")
              this.id_prof='';
              this.id_sub='';
              this.dateAv='';
            } else {
              alert("problema rimozione disponibilità");
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