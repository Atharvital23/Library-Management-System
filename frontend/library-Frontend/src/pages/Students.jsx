import { useState, useEffect } from 'react';
import { toast } from 'react-toastify';
import { PlusIcon, MagnifyingGlassIcon, UsersIcon } from '@heroicons/react/24/outline';
import { PageHeader, Card, Button, LoadingSpinner, StatusBadge, Modal } from '../components/common';
import { studentService } from '../services';
import { DEPARTMENTS } from '../constants/enums';

// Student Roles for the form
const STUDENT_ROLES = [
  { value: 'STUDENT', label: 'Student' },
  { value: 'FACULTY', label: 'Faculty' },
  { value: 'STAFF', label: 'Staff' },
];

function Students() {
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResult, setSearchResult] = useState(null);
  const [searching, setSearching] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  
  // Form data matching backend StudentRequestDTO
  const [formData, setFormData] = useState({
    studentIdCard: '',
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber: '',
    department: '',
    role: 'STUDENT',
  });

  useEffect(() => {
    fetchStudents();
  }, []);

  const fetchStudents = async () => {
    try {
      const data = await studentService.getAll();
      setStudents(data);
    } catch (error) {
      console.error('Error fetching students:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async () => {
    if (!searchTerm.trim()) {
      toast.error('Please enter a student ID card');
      return;
    }

    setSearching(true);
    setSearchResult(null);
    try {
      const data = await studentService.getByCardId(searchTerm);
      setSearchResult(data);
    } catch (error) {
      console.error('Error searching student:', error);
      setSearchResult(null);
    } finally {
      setSearching(false);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.firstName.trim()) {
      toast.error('First Name is required');
      return;
    }
    if (!formData.lastName.trim()) {
      toast.error('Last Name is required');
      return;
    }
    if (!formData.email.trim()) {
      toast.error('Email is required');
      return;
    }
    if (!formData.studentIdCard.trim()) {
      toast.error('Student ID Card is required');
      return;
    }
    if (!formData.department) {
      toast.error('Department is required');
      return;
    }
    if (!formData.role) {
      toast.error('Role is required');
      return;
    }

    setSubmitting(true);
    try {
      await studentService.register(formData);
      toast.success('Student registered successfully');
      setIsModalOpen(false);
      setFormData({
        studentIdCard: '',
        firstName: '',
        lastName: '',
        email: '',
        phoneNumber: '',
        department: '',
        role: 'STUDENT',
      });
      fetchStudents();
    } catch (error) {
      console.error('Error registering student:', error);
    } finally {
      setSubmitting(false);
    }
  };

  // Helper to get full name from student object
  const getFullName = (student) => {
    return `${student.firstName || ''} ${student.lastName || ''}`.trim() || '-';
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center min-h-96">
        <LoadingSpinner size="lg" />
      </div>
    );
  }

  return (
    <div>
      <PageHeader
        title="Students"
        subtitle="Manage library members"
        action={
          <Button onClick={() => setIsModalOpen(true)}>
            <PlusIcon className="h-5 w-5 mr-2" />
            Register Student
          </Button>
        }
      />

      {/* Search Section */}
      <Card className="mb-6">
        <Card.Body>
          <div className="flex flex-col sm:flex-row gap-4">
            <div className="flex-1">
              <label htmlFor="search" className="sr-only">Search by ID Card</label>
              <div className="relative">
                <input
                  type="text"
                  id="search"
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
                  className="block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border pl-10 py-2 pr-4"
                  placeholder="Search by student ID card..."
                />
                <MagnifyingGlassIcon className="h-5 w-5 text-gray-400 absolute left-3 top-1/2 transform -translate-y-1/2" />
              </div>
            </div>
            <Button onClick={handleSearch} loading={searching}>
              Search
            </Button>
          </div>

          {/* Search Result */}
          {searchResult && (
            <div className="mt-4 p-4 bg-gray-50 rounded-lg">
              <h4 className="font-medium text-gray-900">Search Result:</h4>
              <div className="mt-2 grid grid-cols-2 gap-4">
                <div>
                  <span className="text-sm text-gray-500">Name:</span>
                  <p className="text-sm font-medium">{getFullName(searchResult)}</p>
                </div>
                <div>
                  <span className="text-sm text-gray-500">ID Card:</span>
                  <p className="text-sm font-medium">{searchResult.studentIdCard}</p>
                </div>
                <div>
                  <span className="text-sm text-gray-500">Email:</span>
                  <p className="text-sm font-medium">{searchResult.email}</p>
                </div>
                <div>
                  <span className="text-sm text-gray-500">Status:</span>
                  <StatusBadge status={searchResult.status} type="student" />
                </div>
              </div>
            </div>
          )}
        </Card.Body>
      </Card>

      {/* Students Table */}
      <Card>
        <Card.Body className="p-0">
          {students.length === 0 ? (
            <div className="p-12 text-center">
              <UsersIcon className="mx-auto h-12 w-12 text-gray-400" />
              <h3 className="mt-2 text-sm font-medium text-gray-900">No students found</h3>
              <p className="mt-1 text-sm text-gray-500">Get started by registering a new student.</p>
            </div>
          ) : (
            <div className="overflow-x-auto">
              <table className="min-w-full divide-y divide-gray-200">
                <thead className="bg-gray-50">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Name
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      ID Card
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Email
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Department
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Status
                    </th>
                  </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                  {students.map((student) => (
                    <tr key={student.id} className="hover:bg-gray-50">
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                        {getFullName(student)}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                        {student.studentIdCard}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                        {student.email}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                        {student.department?.replace('_', ' ')}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <StatusBadge status={student.status} type="student" />
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </Card.Body>
      </Card>

      {/* Register Student Modal */}
      <Modal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        title="Register New Student"
      >
        <form onSubmit={handleSubmit}>
          <div className="space-y-4">
            <div className="grid grid-cols-2 gap-4">
              <div>
                <label htmlFor="firstName" className="block text-sm font-medium text-gray-700">
                  First Name *
                </label>
                <input
                  type="text"
                  id="firstName"
                  name="firstName"
                  value={formData.firstName}
                  onChange={handleChange}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
                />
              </div>

              <div>
                <label htmlFor="lastName" className="block text-sm font-medium text-gray-700">
                  Last Name *
                </label>
                <input
                  type="text"
                  id="lastName"
                  name="lastName"
                  value={formData.lastName}
                  onChange={handleChange}
                  className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
                />
              </div>
            </div>

            <div>
              <label htmlFor="studentIdCard" className="block text-sm font-medium text-gray-700">
                Student ID Card *
              </label>
              <input
                type="text"
                id="studentIdCard"
                name="studentIdCard"
                value={formData.studentIdCard}
                onChange={handleChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
              />
            </div>

            <div>
              <label htmlFor="email" className="block text-sm font-medium text-gray-700">
                Email *
              </label>
              <input
                type="email"
                id="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
              />
            </div>

            <div>
              <label htmlFor="phoneNumber" className="block text-sm font-medium text-gray-700">
                Phone Number <span className="text-gray-400 font-normal">(10 digits)</span>
              </label>
              <input
                type="tel"
                id="phoneNumber"
                name="phoneNumber"
                value={formData.phoneNumber}
                onChange={handleChange}
                pattern="\d{10}"
                maxLength={10}
                placeholder="1234567890"
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
              />
            </div>

            <div>
              <label htmlFor="department" className="block text-sm font-medium text-gray-700">
                Department *
              </label>
              <select
                id="department"
                name="department"
                value={formData.department}
                onChange={handleChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
              >
                <option value="">Select department</option>
                {DEPARTMENTS.map((dept) => (
                  <option key={dept.value} value={dept.value}>
                    {dept.label}
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label htmlFor="role" className="block text-sm font-medium text-gray-700">
                Role *
              </label>
              <select
                id="role"
                name="role"
                value={formData.role}
                onChange={handleChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border px-3 py-2"
              >
                <option value="">Select role</option>
                {STUDENT_ROLES.map((role) => (
                  <option key={role.value} value={role.value}>
                    {role.label}
                  </option>
                ))}
              </select>
            </div>
          </div>

          <div className="mt-6 flex justify-end gap-3">
            <Button type="button" variant="secondary" onClick={() => setIsModalOpen(false)}>
              Cancel
            </Button>
            <Button type="submit" loading={submitting}>
              Register
            </Button>
          </div>
        </form>
      </Modal>
    </div>
  );
}

export default Students;
