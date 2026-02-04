import api from '../config/axiosConfig';

const transactionService = {
  // Get all transactions
  // GET /api/transactions
  getAll: async () => {
    const response = await api.get('/transactions');
    return response.data;
  },

  // Issue a book
  // POST /api/transactions/issue with BorrowRequestDTO body
  issue: async (qrCodeStr, studentIdCard, issuedBy) => {
    const response = await api.post('/transactions/issue', {
      qrCodeStr,
      studentIdCard,
      issuedBy,
    });
    return response.data;
  },

  // Return a book
  // POST /api/transactions/return?qrCode=...
  return: async (qrCode) => {
    const response = await api.post('/transactions/return', null, {
      params: { qrCode },
    });
    return response.data;
  },

  // Renew a book
  // POST /api/transactions/renew/{transactionId}
  renew: async (transactionId) => {
    const response = await api.post(`/transactions/renew/${transactionId}`);
    return response.data;
  },

  // Pay fine
  // POST /api/transactions/pay-fine/{transactionId}
  payFine: async (transactionId) => {
    const response = await api.post(`/transactions/pay-fine/${transactionId}`);
    return response.data;
  },
};

export default transactionService;
