import Axios from 'axios';

const httpClient = Axios.create({
  baseURL: process.env.NEXT_PUBLIC_BASE_URL, // Base URL do back-end
  headers: {
    'X-Requested-With': 'XMLHttpRequest',
    'Content-Type': 'application/json',
    'Accept': 'application/json',
  },
  withCredentials: true, // Inclui cookies, se necessário
});

export default httpClient;
