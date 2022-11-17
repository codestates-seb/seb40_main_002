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
      borderRadius: {
        btnRadius: '3px',
        ImgRadius: '15px',
        CommentRadius: '10px',
      },
    },
  },

  variants: {
    extend: {},
  },
  plugins: [require('daisyui')],
  daisyui: {
    themes: [
      {
        mytheme: {
          primary: '#6294CD',
          'primary-content': '#FFFFFF',
          secondary: '#F000B8',
          accent: '#37CDBE',
          neutral: '#3D4451',
          'base-100': '#FFFFFF',
          info: '#3ABFF8',
          success: '#36D399',
          warning: '#FBBD23',
          error: '#F87272',
        },
      },
    ],
  },
};
