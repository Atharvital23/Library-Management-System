function StatusBadge({ status, type = 'default' }) {
  const getStatusClasses = () => {
    switch (type) {
      case 'student':
        return status === 'ACTIVE'
          ? 'bg-green-100 text-green-800'
          : 'bg-red-100 text-red-800';
      case 'copy':
        return status === 'AVAILABLE'
          ? 'bg-green-100 text-green-800'
          : 'bg-yellow-100 text-yellow-800';
      case 'transaction':
        if (status === 'RETURNED') return 'bg-green-100 text-green-800';
        if (status === 'OVERDUE') return 'bg-red-100 text-red-800';
        return 'bg-blue-100 text-blue-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  };

  return (
    <span
      className={`inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium ${getStatusClasses()}`}
    >
      {status}
    </span>
  );
}

export default StatusBadge;
