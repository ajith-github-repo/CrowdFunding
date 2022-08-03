module.exports = {
    testPathIgnorePatterns: ["<rootDir>/.next/", "<rootDir>/node_modules/"],
    setupFiles: [`<rootDir>/jest-shim.js`],
    modulePathIgnorePatterns: ["<rootDir>/src/__tests__/__mocks__/","<rootDir>/src/__tests__/helper/"],
    moduleNameMapper: {
        "\\.(jpg|jpeg|png|gif|css|scss)$": "<rootDir>/src/__tests__/__mocks__/emptyMock.js"
      },
    testEnvironment: "jsdom",
    transform: {
        "^.+\\.(js|jsx|ts|tsx)$": "<rootDir>/node_modules/babel-jest",
        '.+\\.(css|styl|less|sass|scss)$': 'jest-css-modules-transform'
    },
    
};