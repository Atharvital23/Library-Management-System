import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import { Layout } from './components/layout';
import {
  Dashboard,
  Categories,
  BookList,
  AddBook,
  BookDetails,
  Students,
  Circulation,
  Transactions,
  AdminUsers,
} from './pages';

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-gray-50">
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route index element={<Dashboard />} />
            <Route path="categories" element={<Categories />} />
            <Route path="books" element={<BookList />} />
            <Route path="books/add" element={<AddBook />} />
            <Route path="books/:id" element={<BookDetails />} />
            <Route path="students" element={<Students />} />
            <Route path="circulation" element={<Circulation />} />
            <Route path="transactions" element={<Transactions />} />
            <Route path="admin" element={<AdminUsers />} />
          </Route>
        </Routes>
        
        <ToastContainer
          position="top-right"
          autoClose={3000}
          hideProgressBar={false}
          newestOnTop
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
          theme="colored"
        />
      </div>
    </Router>
  );
}

export default App;
