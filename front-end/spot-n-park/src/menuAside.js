import {
  mdiMonitor,
  mdiGithub,
  mdiLock,
  mdiAlertCircle,
  mdiSquareEditOutline,
  mdiTable,
  mdiViewList,
  mdiTelevisionGuide,
  mdiResponsive,
  mdiPalette,
  mdiReact,
  mdiParking,
  mdiHome,
  mdiBookMarker,
  mdiCar,
  mdiCashMultiple,
  mdiCalendarMonth,
  mdiAccountMultiple,
  mdiViewDashboard,
  mdiAccount,
} from '@mdi/js'

export default [
  {
    to: '/',
    icon: mdiHome,
    label: 'Home',
    roles: ['basic_user', 'park_admin', 'super_admin']
  },
  {
    to: '/reservations',
    icon: mdiBookMarker,
    label: 'Reservations',
    roles: ['basic_user', 'park_admin', 'super_admin']
  },
  {
    to: '/slots',
    icon: mdiCar,
    label: 'Slots',
    roles: ['park_admin']
  },
  {
    to: '/dashboard',
    icon: mdiMonitor,
    label: 'Dashboard',
    roles: ['park_admin']
  },
  {
    to: '/fees',
    icon: mdiCashMultiple,
    label: 'Fees',
    roles: ['park_admin']
  },
  {
    to: '/schedules',
    icon: mdiCalendarMonth,
    label: 'Schedules',
    roles: ['park_admin']
  },
  {
    to: '/parking-lots',
    icon: mdiParking,
    label: 'Parking Lots',
    roles: ['super_admin']
  },
  {
    to: '/users',
    icon: mdiAccountMultiple,
    label: 'Users',
    roles: ['super_admin']
  },
  {
    label: 'Dropdown',
    icon: mdiViewList,
    menu: [
      {
        label: 'Item One',
      },
      {
        label: 'Item Two',
      },
    ],
  },
  {
    label: 'Templates',
    icon: mdiViewList,
    menu: [
      {
        to: '/tables',
        label: 'Tables',
        icon: mdiTable,
      },
      {
        to: '/forms',
        label: 'Forms',
        icon: mdiSquareEditOutline,
      },
      {
        to: '/ui',
        label: 'UI',
        icon: mdiTelevisionGuide,
      },
      {
        to: '/responsive',
        label: 'Responsive',
        icon: mdiResponsive,
      },
      {
        to: '/',
        label: 'Styles',
        icon: mdiPalette,
      },
      {
        to: '/profile',
        label: 'Profile',
        icon: mdiAccount,
      },
      {
        to: '/login',
        label: 'Login',
        icon: mdiLock,
      },
      {
        to: '/error',
        label: 'Error',
        icon: mdiAlertCircle,
      },
      {
        href: 'https://github.com/justboil/admin-one-vue-tailwind',
        label: 'GitHub',
        icon: mdiGithub,
        target: '_blank',
      },
      {
        href: 'https://github.com/justboil/admin-one-react-tailwind',
        label: 'React version',
        icon: mdiReact,
        target: '_blank',
      },
    ],
  },
  {
    label: 'Menu',
    menu: [
      {
        label: 'Dashboard',
        icon: mdiViewDashboard,
        to: '/'
      },
      {
        label: 'Profile',
        icon: mdiAccount,
        to: '/profile'
      }
    ]
  }
]
