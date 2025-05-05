import {
  mdiAccountCircle,
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
  mdiCarWrench,
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
  },
  {
    to: '/slots',
    icon: mdiCar,
    label: 'Slots',
  },
  {
    to: '/reservations',
    icon: mdiBookMarker,
    label: 'Reservations',
  },
  {
    to: '/dashboard',
    icon: mdiMonitor,
    label: 'Dashboard',
  },
  {
    to: '/fees',
    icon: mdiCashMultiple,
    label: 'Fees',
  },
  {
    to: '/services',
    icon: mdiCarWrench,
    label: 'Services',
  },
  {
    to: '/schedules',
    icon: mdiCalendarMonth,
    label: 'Schedules',
  },
  {
    to: '/parking-lots',
    icon: mdiParking,
    label: 'Parking Lots',
  },
  {
    to: '/users',
    icon: mdiAccountMultiple,
    label: 'Users',
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
