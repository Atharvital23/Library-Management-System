import { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { 
  Bars3Icon, 
  XMarkIcon,
  BookOpenIcon,
  UsersIcon,
  TagIcon,
  ArrowsRightLeftIcon,
  HomeIcon,
  UserPlusIcon,
  ClipboardDocumentListIcon
} from '@heroicons/react/24/outline';

const navigation = [
  { name: 'Dashboard', href: '/', icon: HomeIcon },
  { name: 'Books', href: '/books', icon: BookOpenIcon },
  { name: 'Categories', href: '/categories', icon: TagIcon },
  { name: 'Students', href: '/students', icon: UsersIcon },
  { name: 'Circulation', href: '/circulation', icon: ArrowsRightLeftIcon },
  { name: 'Transactions', href: '/transactions', icon: ClipboardDocumentListIcon },
  { name: 'Admin', href: '/admin', icon: UserPlusIcon },
];

function Navbar() {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const location = useLocation();

  const isActive = (path) => {
    if (path === '/') {
      return location.pathname === '/';
    }
    return location.pathname.startsWith(path);
  };

  return (
    <header className="bg-white shadow-sm lg:hidden">
      <nav className="mx-auto flex max-w-7xl items-center justify-between p-4">
        <div className="flex items-center">
          <BookOpenIcon className="h-8 w-8 text-indigo-600" />
          <span className="ml-2 text-xl font-bold text-gray-900">LibraryMS</span>
        </div>
        <button
          type="button"
          className="inline-flex items-center justify-center rounded-md p-2 text-gray-700 hover:bg-gray-100"
          onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
        >
          <span className="sr-only">Toggle menu</span>
          {mobileMenuOpen ? (
            <XMarkIcon className="h-6 w-6" aria-hidden="true" />
          ) : (
            <Bars3Icon className="h-6 w-6" aria-hidden="true" />
          )}
        </button>
      </nav>
      
      {/* Mobile menu */}
      {mobileMenuOpen && (
        <div className="lg:hidden">
          <div className="space-y-1 px-2 pb-3 pt-2">
            {navigation.map((item) => (
              <Link
                key={item.name}
                to={item.href}
                onClick={() => setMobileMenuOpen(false)}
                className={`
                  flex items-center gap-x-3 rounded-md px-3 py-2 text-base font-semibold
                  ${isActive(item.href)
                    ? 'bg-indigo-50 text-indigo-600'
                    : 'text-gray-700 hover:bg-gray-50 hover:text-indigo-600'
                  }
                `}
              >
                <item.icon className="h-5 w-5" aria-hidden="true" />
                {item.name}
              </Link>
            ))}
          </div>
        </div>
      )}
    </header>
  );
}

export default Navbar;
