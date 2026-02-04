import api from '../config/axiosConfig';

const copyService = {
  // Get all copies for a book
  getByBookId: async (bookId) => {
    const response = await api.get(`/copies/book/${bookId}`);
    return response.data;
  },

  // Add a new copy (using query params)
  add: async (bookId, qrCodeStr, shelfLocation) => {
    const response = await api.post(`/copies/add`, null, {
      params: {
        bookId,
        qrCodeStr,
        shelfLocation,
      },
    });
    return response.data;
  },
};

export default copyService;
