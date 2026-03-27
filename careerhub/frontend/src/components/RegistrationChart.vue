<template>
  <div class="chart-container">
    <canvas ref="chartCanvas"></canvas>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue';
import Chart from 'chart.js/auto';

// Принимаем три параметра: подписи дат, данные студентов и данные компаний
const props = defineProps(['labels', 'studentData', 'companyData']);
const chartCanvas = ref(null);
let chartInstance = null;

const renderChart = () => {
  if (!chartCanvas.value) return;
  if (chartInstance) chartInstance.destroy();

  chartInstance = new Chart(chartCanvas.value, {
    type: 'line',
    data: {
      labels: props.labels,
      datasets: [
        {
          label: 'Студенты',
          data: props.studentData,
          borderColor: '#42b883', // Зеленый
          backgroundColor: 'rgba(66, 184, 131, 0.1)',
          fill: true,
          tension: 0.4,
          pointRadius: 5,
          pointHoverRadius: 8
        },
        {
          label: 'Компании',
          data: props.companyData,
          borderColor: '#3498db', // Синий
          backgroundColor: 'rgba(52, 152, 219, 0.1)',
          fill: true,
          tension: 0.4,
          pointRadius: 5,
          pointHoverRadius: 8
        }
      ]
    },
    options: {
      responsive: true,
      plugins: {
        legend: {
          labels: { color: '#fff' } // Белый цвет текста легенды
        }
      },
      scales: {
        y: {
          beginAtZero: true,
          grid: { color: '#444' },
          ticks: { color: '#aaa' }
        },
        x: {
          grid: { color: '#444' },
          ticks: { color: '#aaa' }
        }
      }
    }
  });
};

onMounted(renderChart);

// Следим за обоими массивами данных
watch(() => [props.studentData, props.companyData, props.labels], renderChart, { deep: true });
</script>

<style scoped>
.chart-container {
  background: #2a2a2a;
  padding: 20px;
  border-radius: 12px;
  margin-top: 20px;
  border: 1px solid #444;
}
</style>