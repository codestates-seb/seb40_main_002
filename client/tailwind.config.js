/** @type {import('tailwindcss').Config} */
module.exports = {
  purge: ['./src/**/*.{js,jsx,ts,tsx}', './public/index.html'],
  content: ['./public/**/*.html', './src/**/*.{js,jsx,ts,tsx}'],
  darkMode: false, // or 'media' or 'class'
  theme: {
    extend: {
      width: {
        search: '650px',
        selectModal: '340px',
        datapicker: '240px',
      },
      height: {
        search: '50px',
        nav: '88px',
        selectModal: '70px',
      },
      colors: {
        point: '#629ECD',
        borderline: '#DDDDDD',
      },
      borderRadius: {
        base: '15px',
        search: '30px',
      },
      fontSize: {
        tag: '14px',
        colors: {
          'point-color': '#6294CD',
          'border-color': '#DDDDDD',
          'font-color': '#717171',
        },
      },
    },
    variants: {
      extend: {},
    },
    plugins: [],
  },
};
