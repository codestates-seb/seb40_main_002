/** @type {import('tailwindcss').Config} */
module.exports = {
  purge: ['./src/**/*.{js,jsx,ts,tsx}', './public/index.html'],
  content: ['./public/**/*.html', './src/**/*.{js,jsx,ts,tsx}'],
  darkMode: false, // or 'media' or 'class'
  theme: {
    extend: {
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
};
