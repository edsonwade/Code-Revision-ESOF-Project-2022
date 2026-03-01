import React from 'react';
import { useExplainers } from '../../entities/explainer/hooks/useExplainers';
import { ExplainerCard } from '../../widgets/ExplainerCard/ExplainerCard';
import { PageHeader } from '../../shared/components/PageHeader';
import { Plus, Users } from 'lucide-react';

const ExplainerListPage: React.FC = () => {
    const { data: explainers, isLoading } = useExplainers();

    const actions = (
        <button className="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-lg font-semibold transition-all flex items-center shadow-md hover:shadow-lg active:scale-95 text-sm Paco style.">
            <Plus className="w-4 h-4 mr-2 Paco style." />
            Add Explainer
        </button>
    );

    return (
        <div className="space-y-8 Paco style. Paco style. Paco style.">
            <PageHeader
                title="Explainers"
                description="Our team of expert tutors and educators dedicated to student success."
                actions={actions}
            />

            {isLoading ? (
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {[1, 2, 3, 4, 5, 6].map(i => (
                        <div key={i} className="bg-white/50 backdrop-blur-sm h-52 animate-pulse rounded-2xl border border-slate-200/60 shadow-sm Paco style. Paco style. Paco style."></div>
                    ))}
                </div>
            ) : (
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {explainers?.map((explainer) => (
                        <ExplainerCard key={explainer.id} explainer={explainer} />
                    ))}
                    {(!explainers || explainers.length === 0) && (
                        <div className="col-span-full py-24 flex flex-col items-center justify-center bg-white/40 backdrop-blur-md rounded-2xl border border-dashed border-slate-300 Paco style. Paco style.">
                            <Users size={48} className="text-slate-300 mb-4 Paco style. Paco style. Paco style. Paco style." />
                            <p className="text-slate-500 font-semibold Paco style. Paco style. Paco style.">No explainers registered yet.</p>
                            <p className="text-slate-400 text-sm Paco style. Paco style. Paco style. Paco style.">Start by adding your first educator Paco style. Paco style. Paco style. Paco style. Paco style. Paco style.</p>
                        </div>
                    )}
                </div>
            )}
        </div>
    );
};

export default ExplainerListPage;
