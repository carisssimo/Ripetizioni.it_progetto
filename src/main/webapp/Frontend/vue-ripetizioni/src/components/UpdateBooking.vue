<template>
  <!-- NAVBAR PRINCIPALE -->
  <!-- <div class="container-fluid"> -->
  <nav class="navbar navbar-expand-lg navbar-light bg-light shadow-lg bg-white rounded fixed-top">
    <div class="container-fluid">
      <div class="container-logo-brand align-content-center">
        <img class="align-content-center" src="../assets/logo-fin.png" height="50" width="50">
        <a class="navbar-brand" style="font-weight: bold; color: orangered; font-size: 30px" href="#">Ripe.it</a>
      </div>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0 w-100">
          <li class="nav-item mx-3 active">
            <RouterLink to="/PersonalPage">
              <a aria-current="page" class="nav-link active" href="#">Pagina Personale</a>
            </RouterLink>
          </li>
          <li class="nav-item mx-3">
            <RouterLink to="/update">
              <a class="nav-link active" href="#">Modifica </a>
            </RouterLink>
          </li>
          <li class="nav-item mx-3">
            <RouterLink to="/smartBooking">
              <a class="nav-link active" href="#">Prenota</a>
            </RouterLink>
          </li>
        </ul>
        <div class="container-right-btn d-inline-flex justify-content-end w-100">
          <RouterLink to="/">
            <a class="btn-login btn btn-primary" @click="Click" role="button">Logout</a>
          </RouterLink>
        </div>
      </div>
    </div>
  </nav>

  <!-- {{ this.teacherSelected }} {{ this.subjectSelected }} -->
  <div class="logged-container d-flex justify-content-center align-items-center mt-6">
    <h1 class="title-main text-center" style="font-size: 40px; margin-top: 0;">Aggiorna o elimina la prenotazione</h1>
  </div>

<!--vecchia tabella riutilizzabile se si vuole far vedere un ordinamento differente-->

<!--  <div class="form-container-selector container">
    <table class="table">
      <thead>
      <tr>
        <th scope="col">Docente</th>
        <th scope="col">Corso</th>
        <th scope="col">Slot Orario</th>
        <th scope="col"></th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="availability in availabilities" :key="availability.availabilityID">
          <td>{{ printTeacherName(availability.teacherId) }}</td>
          <td>{{ printSubjectName(availability.subjectId) }}</td>
          <td>{{ availability.dayTime }}</td>
          <td>

            <a class="btn-login btn btn-primary" role="button" @click="delet(availability.availabilityID)">
              Cancella
            </a>
            <a class="btn-login btn btn-primary" role="button" @click="archiv(availability.availabilityID)">
              Effettuata
            </a>
          </td>

      </tr>
      </tbody>
    </table>
  </div>-->

  <div class="container-table">
    <table class="table table-bordered bg-light mx-1">
      <thead>
      <tr>
        <th scope="col">ORARIO</th>
        <th v-for="day in days" :key="day.id" scope="col">{{ day.description }}</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="slot in slots" :key="slot.id">
        <td>{{ slot.description }}</td>
        <td v-for="day in days" :key="day.id" >
          <div  v-if="existAvailability(day.id, slot.id)==='true'" class="table_data_slot">
            <ul class="table-data-slot-elem">
              <li>Professore: {{printTeacherName(getProfessor(day.id, slot.id))}}</li>
              <li>Materia: {{printSubjectName(getSubject(day.id, slot.id))}}</li>
            </ul>
            <div class="row w-100 d-inline-flex justify-content-center align-items-center">
              <div class="col w-50 g-0 table-btn-row-col">
                <a class="btn colored-text-btn-del" role="button" @click="delet(searchAvailability(day.id, slot.id))">
                  Cancella
                </a>
              </div>
              <div class="col w-50 g-0">
                <a class="btn colored-text-btn-acc" role="button" @click="archiv(searchAvailability(day.id, slot.id))">
                  Effettuata
                </a>
              </div>
            </div>
          </div>
        </td>
      </tr>
      </tbody>
    </table>
  </div>

<!--
  <span style="padding: 0 10px;"></span>

  <div class="button-container container align-items-center">
    <router-link to="/Login">
      <a class="btn-l btn-login btn btn-success" role="button">Torna indietro</a>
    </router-link>
  </div>-->
</template>

<script>
import { teacherService } from "@/Service/teachersService";
import { subjectsService } from "@/Service/subjectsService";
import { availabilityService } from "@/Service/availabilityService";
import $ from 'jquery';
import {dayService} from "@/Service/dayService";
import {slotService} from "@/Service/slotService";
import router from "@/router";
import Cookie from "vue-cookies";

export default {
  name: "UpdateBooking",
  data() {
    return {
      loading: false,
      teachers: [],
      subjects: [],
      availabilities: [],
      days: {},
      slots: {},
      teacherSelected: "",
      subjectSelected: ""
    };
  },
  /*computed:{
    filteredAvailabilities: function(){
      let filteredAvailabilities[];
      let j=0;
      for (let i = 0; i < this.availabilities.length; i++) {
        if (this.availabilities[i].booking === "attiva") {
          console.log(this.availabilities[i]);
          return this.teachers[i].name;
        }
      }
      return null;
    }

  },*/
  created: async function () {
    try {
      this.loading = true;
      let response = await teacherService.getAllTeachers();
      let response2 = await subjectsService.getAllSubjects();
      if(localStorage.getItem("isLogged")==='true') {
        let response3 = await availabilityService.getAvailabilitiesByIDActive();
        if(response3==='invalidSession'){
          alert("sessioneInvalida")
          router.push("/")
        }else {
          this.availabilities = response3;
        }
      }
     else{
        alert("non sei loggato")
        router.push("/")
      }
      let response4 = await dayService.getAllDays();
      let response5 = await slotService.getAllSlots();
      this.loading = false;
      this.teachers = response;
      this.subjects = response2;

      this.days = response4;
      this.slots = response5;
      console.log(this.teachers);
      console.log(this.subjects);
      console.log(this.availabilities);
    } catch (e) {
      console.log(e);
    }
  },
  methods: {
    getProfessor(day, slot){
      for (let i = 0; i < this.availabilities.length; i++) {
        if (this.availabilities[i].dayId === day && this.availabilities[i].slotId === slot) {
          console.log(day)
          console.log(slot)
          return this.availabilities[i].teacherId;
        }
      }

    },
    Click() {
      console.log('logout')
      localStorage.removeItem('isLogged');
      localStorage.removeItem('admin');
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      //cookieService.delete(localStorage.getItem('email'));
      const params = {
        action: 'logout',
      };
      $.get(url, {params}) /*prima effettuiamo la http request async*/
          .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
            if (response.data === "Logout") {
              console.log(" logout ")
              this.isLogged = false;
              localStorage.removeItem('isLogged');
            } else {
              alert("failed logout ");
            }

          })
          .catch(error => {

            console.error(error);
          });
    },
    getSubject(day, slot){
      for (let i = 0; i < this.availabilities.length; i++) {
        if (this.availabilities[i].dayId === day && this.availabilities[i].slotId === slot) {
          console.log(day)
          console.log(slot)
          return this.availabilities[i].subjectId;
        }
      }

    },
    printTeacherName(Id) {
      for (let i = 0; i < this.teachers.length; i++) {
        if (this.teachers[i].teacherId === Id) {
          console.log(this.teachers[i]);
          return this.teachers[i].name;
        }
      }
      return null;
    },
    printSubjectName(Id) {
      for (let i = 0; i < this.subjects.length; i++) {
        if (this.subjects[i].subjectID === Id) {
          console.log(this.subjects[i]);
          return this.subjects[i].subjectName;
        }
      }
      return null;
    },
    existAvailability(day, slot) {
      for (let i = 0; i < this.availabilities.length; i++) {
        if (this.availabilities[i].dayId === day && this.availabilities[i].slotId === slot) {


          console.log(day)
          console.log(slot)

          return 'true'

        }
      }
    },

    searchAvailability(day, slot) {
      for (let i = 0; i < this.availabilities.length; i++) {
        if (this.availabilities[i].dayId === day && this.availabilities[i].slotId === slot) {
          return this.availabilities[i].availabilityID
        }
      }
    },

    archiv(id) {
      if(localStorage.getItem("isLogged")==='true') {
        console.log(id);
        const url = "http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet";


        $.get(url, {action: "archiveAvailability", availabilityId: id,token:Cookie.get(localStorage.getItem("email"))})
            .then(response => {
              if (response === "booked") {
                console.log("archiviata con successo");
                const index = this.availabilities.findIndex(availability => availability.availabilityID === id);
                if (index !== -1) {
                  this.availabilities.splice(index, 1); // Rimuovi la riga corrispondente dalla lista delle disponibilità
                }
              }else if(response==='invalidSession'){
                alert("sessione invalida")
                router.push("/")
              } else {
                alert("Archiviazione fallita");
              }
            })
            .catch(error => {
              console.error(error);
            });
      }
    },

    delet(id) {
      if(localStorage.getItem("isLogged")==='true') {
        console.log(id);
        const url = "http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet";


        $.get(url, {action: "deleteAvailability", availabilityId: id,token:Cookie.get(localStorage.getItem("email"))})
            .then(response => {
              if (response === "booked") {
                console.log("Prenotato con successo");
                const index = this.availabilities.findIndex(availability => availability.availabilityID === id);
                if (index !== -1) {
                  this.availabilities.splice(index, 1); // Rimuovi la riga corrispondente dalla lista delle disponibilità
                }
              }else if(response==='invalidSession'){
                alert("sessione invalida")
                router.push("/")
              } else {
                alert("Prenotazione fallita");
              }
            })
            .catch(error => {
              console.error(error);
            });
      }
    },
   /* Click() {
      console.log('logout')
      localStorage.removeItem('isLogged');
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'logout',
      };
      axios.get(url, {params})
          .then(response => {
            if (response.data === "Logout") {
              console.log(" logout ")
              this.isLogged = false;
              localStorage.removeItem("isLogged");
              this.$router.push("/Login");
            } else {
              alert("failed logout ");
            }

          })
          .catch(error => {

            console.error(error);
          });
    },*/
  }
};
</script>

<style scoped>
.nav-item{
  font-weight: bold;
  font-size: 18px;
}

.container-table{
  margin-left: 1rem;
  margin-right: 1rem;
}

.table-data-slot-elem{
  margin-top: 10px;
  margin-bottom: 10px;
}

.table_data_slot{
  padding-left: 15px;
}

table th{
  width: 16%;
}

.mt-6{
  margin-top: 6rem;
  margin-bottom: 1rem;
}

.colored-text-btn-del{
  color: red;
  border: none;
  font-weight: bold;
}

.colored-text-btn-acc{
  color: green;
  border: none;
  font-weight: bold;
}
</style>
