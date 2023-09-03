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
    <h3>Ripetizioni attive, disdette, effettuate</h3>
    <div class="container-table">
      <table class="table table-bordered bg-light mx-1 mt-5">
        <thead>
        <tr>
          <th class="time-slot-table" scope="col">ORARIO</th>
          <th v-for="day in days" :key="day.id" scope="col">{{ day.description }}</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="slot in slots" :key="slot.id">
          <td>{{ slot.description }}</td>
          <td v-for="day in days" :key="day.id">
            <div v-if="existAvailability(day.id,slot.id)==='true'" class="table_data_slot">
              <ul>
                <li>Professore: {{printTeacherName(getProfessor(day.id,slot.id))}}</li>
                <li>Docente: {{printSubjectName(getSubject(day.id,slot.id))}}</li>
                <li>Stato: {{getState(day.id,slot.id)}}</li>
              </ul>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import {availabilityService} from "@/Service/availabilityService";
import {teacherService} from "@/Service/teachersService";
import {subjectsService} from "@/Service/subjectsService";
import {dayService} from "@/Service/dayService";
import {slotService} from "@/Service/slotService";

export default {
  name: 'UserStory',
  data: function(){
    return{
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
      let userId= this.$route.params.userId;
      console.log(userId)
      this.loading = true;
      if(localStorage.getItem("isLogged")==='true' && localStorage.getItem('admin')==='true') {
        let response = await availabilityService.getAvailabilitiesByID2(userId);
        this.availabilities = response.data;
        console.log(response.data);
      }

      let response1 = await teacherService.getAllTeachers();
      let response2 = await subjectsService.getAllSubjects();
      let response3 = await dayService.getAllDays();
      let response4 = await slotService.getAllSlots();
      this.loading = false;
      this.teachers = response1;
      this.subjects = response2;

      this.days = response3;
      this.slots = response4;
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

  }}
</script>

<style>
.container-btn-group{
  display: inline-block;
  float: right;
}

.time-slot-table{
  width: 11%;
}

table th{
  width: 17%;
}

table td{
  padding: 0;
}

.table_data_slot{
  margin: 0;
}

ul{
  padding: 0;
  margin-left: 15px;
}
</style>
