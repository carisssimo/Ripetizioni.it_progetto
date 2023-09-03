<template>
  <!--NAVBAR PRINCIPALE-->
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

  <div class="logged-container d-flex justify-content-center align-items-center mt-6">
    <h1 class="title-main text-center" style="font-size: 40px; margin-top: 0;">Ecco qui il resoconto delle tue prenotazioni!</h1>
  </div>

  <!--  tabella basic da modificare-->

<!--  <div class="form-container-selector container">
    <table class="table">
      <thead>
      <tr>
        <th scope="col">Docente</th>
        <th scope="col">Corso</th>
        <th scope="col">Giorno</th>
        <th scope="col">Orario</th>
        <th scope="col">Stato</th>
        <th scope="col"></th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="av in availabilities" :key="av.id">
        <td>{{ printTeacherName(av.teacherId) }}</td>
        <td>{{ printSubjectName(av.subjectId) }}</td>
        <td>{{ printDayDescription(av.dayId) }}</td>
        <td>{{ printSlotDescription(av.slotId) }}</td>
        <td>{{ av.booking }}</td>
      </tr>
      </tbody>

    </table>
  </div>-->

  <!--  nuova tabella -->

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
        <td v-for="day in days" :key="day.id">
          <div v-if="existAvailability(day.id,slot.id)==='true'" class="table_data_slot">
            <ul class="table-data-slot-elem">
              <li>Professore: {{printTeacherName(getProfessor(day.id,slot.id))}}</li>
              <li>Materia: {{printSubjectName(getSubject(day.id,slot.id))}}</li>
              <li>Stato: {{getState(day.id,slot.id)}}</li>
            </ul>
          </div>
        </td>
      </tr>
      </tbody>
    </table>
  </div>

</template>

<script>
import {availabilityService} from "@/Service/availabilityService";
import $ from 'jquery';
import {teacherService} from "@/Service/teachersService";
import {subjectsService} from "@/Service/subjectsService";
import {dayService} from "@/Service/dayService";
import {slotService} from "@/Service/slotService";
//import {cookieService} from "@/Service/cookieService"


export default {
  // eslint-disable-next-line vue/multi-word-component-names

  name: "PersonalPage",
  data: function () {
    return {
      loading: false,
      teachers: {},
      subjects: {},
      availabilities: [],
      slots: {},
      days: {}
    }
  },

  created: async function () {
    try {
      this.loading = true;
      let response = await availabilityService.getAvailabilitiesByID();
      this.availabilities = response.data;
      let response1 = await teacherService.getAllTeachers();
      let response2 = await subjectsService.getAllSubjects();
      let response3 = await dayService.getAllDays();
      let response4 = await slotService.getAllSlots();
      this.loading = false;
      this.teachers = response1;
      this.subjects = response2;
      this.availabilities = response.data;
      this.days = response3;
      this.slots = response4;
      console.log(this.teachers);
      console.log(this.subjects);
      console.log(this.availabilities);
      console.log(response.data);
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
    
    getSubject(day, slot){
      for (let i = 0; i < this.availabilities.length; i++) {
        if (this.availabilities[i].dayId === day && this.availabilities[i].slotId === slot) {
          console.log(day)
          console.log(slot)
          return this.availabilities[i].subjectId;
        }
      }

    },
    getState(day, slot){
      for (let i = 0; i < this.availabilities.length; i++) {
        if (this.availabilities[i].dayId === day && this.availabilities[i].slotId === slot) {
          console.log(day)
          console.log(slot)
          return this.availabilities[i].booking;
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
    printDayDescription(Id) {
      for (let i = 0; i < this.days.length; i++) {
        if (this.days[i].id === Id) {
          console.log(this.days[i]);
          return this.days[i].description;
        }
      }
      return null;
    },

    printSlotDescription(Id) {
      for (let i = 0; i < this.slots.length; i++) {
        if (this.slots[i].id === Id) {
          console.log(this.slots[i]);
          return this.slots[i].description;
        }
      }
      return null;
    },

    createId(dayId,slotId){
      console.log(dayId+" "+slotId)
      return dayId+" "+slotId
    },

    existAvailability(day, slot) {
      for (let i = 0; i<this.availabilities.length; i++) {
        if (this.availabilities[i].dayId === day && this.availabilities[i].slotId === slot) {


            console.log(day)
            console.log(slot)

            return 'true'

        }
      }
    },


    booking(id) {
      console.log(id)
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'bookingAvailability',
        availabilityId: id,
      };


      if (localStorage.getItem("isLogged") === "true") {  //controllo se è effettivamente un utente loggato
        $.get(url, {params}) /*prima effettuiamo la http request async*/
            .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
              if (response.data === "isBooked") {
                console.log(" prenotato con successo ")
                this.isLogged = true;
              } else {
                alert("prenotazione fallita ");
              }

            })
            .catch(error => {

              console.error(error);
            });
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

  }
}
</script>

<style scoped>
.logged-container{
  margin: 0;
}

.table_data_slot{
  padding: 0;
}

.mt-6{
  margin-top: 6rem;
  margin-bottom: 1rem;
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
  padding-left: 10px;
}

.nav-item{
  font-weight: bold;
  font-size: 18px;
}

table th{
  width: 16%;
}
</style>