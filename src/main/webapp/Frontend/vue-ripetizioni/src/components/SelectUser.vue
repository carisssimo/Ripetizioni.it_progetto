<template>
  <div class="form-container container rounded py-4 mt-4">
    <div class="container-btn-group">
      <div class="btn-group btn-group-justified" role="group">
        <div class="btn-group" role="group">
          <RouterLink :to="'/'+this.selected">
            <a class="btn-l btn-login btn btn-success" role="button">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-left" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
              </svg>
            </a>
          </RouterLink>
        </div>
        <div class="btn-group" role="group">
          <RouterLink :to="'/'+this.selected">
            <a class="btn-l btn-login btn btn-success" role="button">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-right" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708z"/>
              </svg>
            </a>
          </RouterLink>
        </div>
      </div>
    </div>
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

export default {
  name: "SelectUser",
  data(){
    return{
      users:{}
    }
  },
  created: async function(){
    try{
      let response=await userService.getAllUsers();
      this.users=response.data ;
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