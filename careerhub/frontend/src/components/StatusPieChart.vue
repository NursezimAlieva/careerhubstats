<template>
  <div class="pie-container">
    <Pie :data="chartData" :options="chartOptions" />
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { Pie } from 'vue-chartjs';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';

ChartJS.register(ArcElement, Tooltip, Legend);

const props = defineProps({
  searching: Number,
  interns: Number,
  working: Number
});

const chartData = computed(() => ({
  labels: ['В поиске', 'Стажеры', 'Работают'],
  datasets: [{
    backgroundColor: ['#f1c40f', '#3498db', '#42b883'],
    hoverOffset: 10,
    data: [props.searching, props.interns, props.working]
  }]
}));

const chartOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: { position: 'bottom', labels: { color: '#fff', padding: 20 } }
  }
};
</script>

<style scoped>
.pie-container { height: 250px; padding: 10px; }
</style>