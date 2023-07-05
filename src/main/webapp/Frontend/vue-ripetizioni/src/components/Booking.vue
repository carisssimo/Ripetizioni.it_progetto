<template>
  <!--NAVBAR PRINCIPALE-->
  <!--<div class="container-fluid">-->
  <nav class="navbar navbar-light bg-light">
    <h1 class="title-ripetizioni">Ripetizioni.it</h1>
    <form class="form-inline">

      <a class="btn-login btn btn-primary" role="button">Registrati</a>
    </form>
  </nav>
  <!--</div>-->

<!--  {{this.teacherSelected}}
  {{this.subjectSelected}}-->
  <div class="logged-container title-container container">
    <h1 class="title-main">Ora scegli la materia e il professore, poi prenotati !</h1>
  </div>

  <!--FORM per selezionare orario e materia -->
  <div class="form-container-selector container">
    <form class="form-inline">
      <label class="my-1 mr-2" for="inlineFormCustomSelectCourse">Materia</label>
      <select id="inlineFormCustomSelectCourse" class="custom-select my-1 mr-sm-2" v-model="filters.subjectFilter.value">
        <option selected>Scegli la materia</option>
        <option v-for="subject in subjects" :value="subject.subjectID" :key="subject.subjectID" >{{ subject.subjectName }}</option>
      </select>

      <label class="my-1 mr-2" for="inlineFormCustomSelectTime">Professore</label>
      <select id="inlineFormCustomSelectTime" class="custom-select my-1 mr-sm-2" v-model="filters.teacherFilter.value">
        <option selected>Scegli professore</option>
        <option v-for="teacher in teachers" :value="teacher.teacherId" :key="teacher.name" >{{ teacher.name }} {{ teacher.surname }}
        </option>
      </select>


      <button class="btn-login btn btn-primary my-1" type="submit" >Submit</button>
    </form>
  </div>

  <div class="form-container-selector container">
    <VTable
        :data="availabilities"
        :filters="filters"
        class="table">
      <template #head >
      <tr>
        <th scope="col">Docente</th>
        <th scope="col">Corso</th>
        <th scope="col">Slot Orario</th>
        <th scope="col"></th>
      </tr>
      </template>
      <template #body="{ rows }" >
      <tr v-for="row in rows" :key="row.availabilityID">
        <td>{{ printTeacherName(row.teacherId) }}</td>
        <td>{{ printSubjectName(row.subjectId) }}</td>
        <td>{{ row.dayTime }}</td>
        <td><a class="btn-login btn btn-primary" role="button" @click="booking(row.availabilityID)">Prenota</a></td>
      </tr>

      </template>
    </VTable>
  </div>


</template>

<script>
import {teacherService} from "@/Service/teachersService";
import {subjectsService} from "@/Service/subjectsService";
import {availabilityService} from "@/Service/availabilityService";
import axios from "axios";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Booking",
  data: function () {
    return {
      loading: false,
      teachers: {},
      subjects: {},
      availabilities: {},
      filters: {/*  utilizzo di filtri per filtrare la tabella a seconda delle richieste dell'utente'*/
        subjectFilter: { value: '', keys: ['subjectId'] },
        teacherFilter: { value: '', keys: ['teacherId'] }
      }
    }
  },
  created: async function () {
    try {
      this.loading = true;
      let response = await teacherService.getAllTeachers();
      let response2 = await subjectsService.getAllSubjects();
      let response3 = await availabilityService.getAllAvailabilitiesAvailable()
      this.loading = false;
      this.teachers = response.data;
      this.subjects = response2.data;
      this.availabilities = response3.data;
      console.log(this.teachers);
      console.log(this.subjects);
      console.log(this.availabilities);

    } catch (e) {
      console.log(e);
    }
  },
  methods: {
    printTeacherName(Id){
      for (let i = 0; i < this.teachers.length; i++) {
        if (this.teachers[i].teacherId === Id) {
          console.log(this.teachers[i]);
          return this.teachers[i].name;
        }
      }
      return null;
    },
    printSubjectName(Id){
      for (let i = 0; i < this.subjects.length; i++) {
        if (this.subjects[i].subjectID === Id) {
          console.log(this.subjects[i]);
          return this.subjects[i].subjectName;
        }
      }
      return null;
    },
    booking(id) {
      console.log(id)
      const url = 'http://localhost:8080/ServletJDBCmaven_war_exploded/HelloServlet';
      const params = {
        action: 'bookingAvailability',
        availabilityId: id,
      };


      if (localStorage.getItem("isLogged") === "true") {
        axios.get(url, {params}) /*prima effettuiamo la http request async*/
            .then(response => {         /*solo una volta eseguita la request passiamo a gestire la risposta*/
              if (response.data === "booked") {
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

    }
  }
}
</script>

<style scoped>

</style>