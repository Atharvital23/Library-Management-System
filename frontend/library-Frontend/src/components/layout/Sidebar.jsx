import { Link, useLocation } from 'react-router-dom';
import { 
  BookOpenIcon, 
  UsersIcon, 
  TagIcon, 
  ArrowsRightLeftIcon,
  HomeIcon,
  UserPlusIcon,
  Cog6ToothIcon,
  ClipboardDocumentListIcon
} from '@heroicons/react/24/outline';

const navigation = [
  { name: 'Dashboard', href: '/', icon: HomeIcon },
  { name: 'Books', href: '/books', icon: BookOpenIcon },
  { name: 'Categories', href: '/categories', icon: TagIcon },
  { name: 'Students', href: '/students', icon: UsersIcon },
  { name: 'Circulation', href: '/circulation', icon: ArrowsRightLeftIcon },
  { name: 'Transactions', href: '/transactions', icon: ClipboardDocumentListIcon },
  { name: 'Admin Users', href: '/admin', icon: UserPlusIcon },
];

function Sidebar() {
  const location = useLocation();

  const isActive = (path) => {
    if (path === '/') {
      return location.pathname === '/';
    }
    return location.pathname.startsWith(path);
  };

  return (
    <div className="hidden lg:fixed lg:inset-y-0 lg:z-50 lg:flex lg:w-64 lg:flex-col">
      <div className="flex grow flex-col gap-y-5 overflow-y-auto bg-indigo-600 px-6 pb-4">
        <div className="flex h-16 shrink-0 items-center">
          <BookOpenIcon className="h-8 w-8 text-white" />
          <span className="ml-3 text-xl font-bold text-white">LibraryMS</span>
        </div>
        <nav className="flex flex-1 flex-col">
          <ul role="list" className="flex flex-1 flex-col gap-y-7">
            <li>
              <ul role="list" className="-mx-2 space-y-1">
                {navigation.map((item) => (
                  <li key={item.name}>
                    <Link
                      to={item.href}
                      className={`
                        group flex gap-x-3 rounded-md p-2 text-sm font-semibold leading-6
                        ${isActive(item.href)
                          ? 'bg-indigo-700 text-white'
                          : 'text-indigo-200 hover:bg-indigo-700 hover:text-white'
                        }
                      `}
                    >
                      <item.icon
                        className={`h-6 w-6 shrink-0 ${
                          isActive(item.href) ? 'text-white' : 'text-indigo-200 group-hover:text-white'
                        }`}
                        aria-hidden="true"
                      />
                      {item.name}
                    </Link>
                  </li>
                ))}
              </ul>
            </li>
            <li className="mt-auto">
              <Link
                to="/settings"
                className="group -mx-2 flex gap-x-3 rounded-md p-2 text-sm font-semibold leading-6 text-indigo-200 hover:bg-indigo-700 hover:text-white"
              >
                <Cog6ToothIcon
                  className="h-6 w-6 shrink-0 text-indigo-200 group-hover:text-white"
                  aria-hidden="true"
                />
                Settings
              </Link>
            </li>
          </ul>
        </nav>
      </div>
    </div>
  );
}

export default Sidebar;
