/** @type {import('tailwindcss').Config} */
module.exports = {
  // purge: ['./src/**/*.{js,jsx,ts,tsx}', './public/index.html'],
  content: ['./public/**/*.html', './src/**/*.{js,jsx,ts,tsx}'],
  darkMode: false, // or 'media' or 'class'
  theme: {
    extend: {},
  },
  variants: {
    extend: {},
  },
  plugins: [],
};
