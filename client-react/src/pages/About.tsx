import React from 'react';

const About: React.FC = () => {
  return (
    <div className="min-h-screen bg-white">
      <div className="max-w-4xl mx-auto px-4 py-16">
        <h1 className="text-4xl font-light text-center mb-12">About Everlane</h1>
        
        <div className="space-y-8 text-lg leading-relaxed text-gray-700">
          <p>
            We're on a mission to clean up the industry by being radically transparent 
            about our costs, our factories, and our environmental impact.
          </p>
          
          <p>
            Every piece we create is designed to last, made with the finest materials 
            at the best factories, and priced as low as possible.
          </p>
          
          <div className="grid md:grid-cols-2 gap-12 mt-16">
            <div>
              <h2 className="text-2xl font-light mb-4">Our Mission</h2>
              <p className="text-gray-600">
                To prove that you can create timeless, well-made clothing at 
                honest prices while being completely transparent about the process.
              </p>
            </div>
            
            <div>
              <h2 className="text-2xl font-light mb-4">Our Values</h2>
              <ul className="space-y-2 text-gray-600">
                <li>• Radical Transparency</li>
                <li>• Exceptional Quality</li>
                <li>• Ethical Production</li>
                <li>• Timeless Design</li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default About;