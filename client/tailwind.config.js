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
      fontSize: {
        sm: '11px',
        base: '15px',
        lg: '18px',
        xl: '26px',
      },
    },
  },

  variants: {
    extend: {},
  },
  plugins: [],
};
