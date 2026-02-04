import axios from 'axios';
import { toast } from 'react-toastify';

const api = axios.create({
  baseURL: '/api',  // Use relative URL - Vite proxy will forward to localhost:8080
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor - log request for debugging
api.interceptors.request.use(
  (config) => {
    console.log('API Request:', config.method?.toUpperCase(), config.url, config.data);
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor for error handling
api.interceptors.response.use(
  (response) => {
    console.log('API Response:', response.status, response.data);
    return response;
  },
  (error) => {
    console.error('API Error:', error.response?.status, error.response?.data);
    
    // Handle the global error JSON from backend
    if (error.response) {
      const responseData = error.response.data;
      
      // Check for validation errors (Spring Boot returns these differently)
      if (responseData.errors && Array.isArray(responseData.errors)) {
        // Multiple validation errors
        responseData.errors.forEach(err => toast.error(err));
      } else if (responseData.message) {
        // Single error message
        toast.error(responseData.message);
      } else if (typeof responseData === 'string') {
        toast.error(responseData);
      } else {
        toast.error(`Error: ${error.response.status} - ${error.response.statusText}`);
      }
    } else if (error.request) {
      // Network error
      toast.error('Network error. Please check if backend is running on port 8080.');
    } else {
      toast.error('An unexpected error occurred');
    }
    
    return Promise.reject(error);
  }
);

export default api;
