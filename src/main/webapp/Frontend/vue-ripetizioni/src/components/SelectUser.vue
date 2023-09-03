<template>
  <div class="form-container container rounded py-4 mt-4">
    <h3>Seleziona l'utente per visualizzare il suo storico delle ripetizioni: </h3>
    <div class="container-table">
      <table class="table table-bordered bg-light mt-3">
        <thead>
        <tr>
          <th scope="col">ID</th>
          <th scope="col">Nome</th>
          <th scope="col">Cognome</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for=" user in users" :key="user.userID">
          <td>{{ user.userID}}</td>
          <td>{{ user.name }}</td>
          <td>{{ user.surname }}</td>
          <router-link :to="'/Admin/'+user.userID"><td><a class="btn-login btn btn-primary" role="button" >Seleziona</a></td></router-link>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import {userService} from "@/Service/userService";
import router from "@/router";

export default {
  name: "SelectUser",
  data(){
    return{
      users:{}
    }
  },
  created: async function(){
    try{
      if(localStorage.getItem("isLogged")==='true' && localStorage.getItem("admin")==='true') {
        let response = await userService.getAllUsers();
        if(response.data==='notAdmin'){
          alert("sessione invalida")
          router.push("/")
        }
        this.users = response.data;
      }
      console.log(this.users);

    }catch(e){
      console.log(e);
    }

  }
}
</script>

<style scoped>
table th{
  width: 33%;
}

.container-btn-group{
  display: inline-block;
  float: right;
}
</style>